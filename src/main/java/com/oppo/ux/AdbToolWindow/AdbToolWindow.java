package com.oppo.ux.AdbToolWindow;

import com.intellij.openapi.ui.Messages;
import com.oppo.ux.InstructionLoader.InstructionLoader;
import com.oppo.ux.InstructionLoader.InstructionNode;
import com.oppo.ux.InstructionLoader.InstructionResult;
import com.oppo.ux.Utils.StringUtils;
import com.oppo.ux.XmlChooser.XmlFileChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public class AdbToolWindow {

    public static final int OPERATOR_PANEL_PADDING = 10;
    public static final int OPERATOR_PANEL_HEIGHT = 150;
    public static final int OPERATOR_PANEL_WIDTH = 300;
    public static final int RESULT_PANEL_NAX_HEIGHT = 500;

    private JPanel mainPanel;

    private JPanel ConfigXmlSelectorPanel;
    private JTextArea ConfigXmlFilePathText;
    private JButton SelectBtn;
    private JButton RefreshBtn;

    private JScrollPane OperatorScrollPanel;
    private JPanel InstructionsPanel;
    private JPanel ContentPanel;
    private JTextArea ResultTextArea;
    private JScrollPane ResultScrollPanel;

    public AdbToolWindow() {
        SelectBtn.addActionListener(e -> {
            showFileChooserDialog();
        });
        RefreshBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateOperatorPanel(new File(ConfigXmlFilePathText.getText()));
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void showFileChooserDialog() {
        XmlFileChooser fc = new XmlFileChooser("D:\\Code\\plugin\\AdbTools\\");
        fc.setOnChosenListener(isOk -> {
            if (isOk) {
                System.out.println("ok");
                ConfigXmlFilePathText.setText(fc.getSelectedFile().toString());
                updateOperatorPanel(new File(fc.getSelectedFile().toString()));
            } else {
                System.out.println("cancel");
                ConfigXmlFilePathText.setText("null");
            }
        });
        fc.showOpenDialog(null);
    }

    private void updateOperatorPanel(File file) {
        if (file == null) {
            return;
        }
        List<InstructionNode> instructions =
                InstructionLoader.loadConfigXmlFile(file);
        if (instructions == null) {
            return;
        }
        InstructionsPanel.removeAll();
        InstructionsPanel.setPreferredSize(new Dimension(
                OPERATOR_PANEL_WIDTH,
                OPERATOR_PANEL_HEIGHT * instructions.size()
        ));
        InstructionsPanel.setLayout(new FlowLayout(FlowLayout.LEADING, OPERATOR_PANEL_PADDING, OPERATOR_PANEL_PADDING));
        for (InstructionNode instruction : instructions) {
            System.out.println("instruction = " + instruction);
            OperatorPanel operatorPanel = new OperatorPanel(instruction, new OperatorPanel.OnInstructionExecutedListener() {
                @Override
                public void onExecuted(List<InstructionResult> instructionResults) {
                    updateResultPanel(instructionResults);
                }
            });
            InstructionsPanel.add(operatorPanel);
        }
        InstructionsPanel.revalidate();
        InstructionsPanel.repaint();
    }

    private void updateResultPanel(List<InstructionResult> instructionResults) {
        StringBuilder resultText = new StringBuilder();
        for (InstructionResult result : instructionResults) {
            if (resultText.length() != 0) {
                resultText.append("\n");
            }
            resultText.append(
                    "--------------------------------------------------------\n" +
                            " > " + result.instructionCode + "\n" +
                            "--------------------------------------------------------\n" +
                            result.normalMessage + "\n" +
//                    "-- > error : " + result.normalMessage + "\n" +
                            "--------------------------------------------------------\n");
        }
        int lineCount = StringUtils.countStr(resultText.toString(), "\n");
        ResultTextArea.setText(resultText.toString());
        if (lineCount > 25) {
            ResultScrollPanel.setPreferredSize(new Dimension(
                    OPERATOR_PANEL_WIDTH, RESULT_PANEL_NAX_HEIGHT
            ));
        }
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showDialog() {
        Messages.showMessageDialog(
                "This is a dialog window",
                "Adb Tools",
                Messages.getInformationIcon()
        );
    }

}
