package com.oppo.ux.AdbToolWindow;

import com.oppo.ux.AdbExecuter.AdbExecutor;
import com.oppo.ux.InstructionLoader.InstructionNode;
import com.oppo.ux.InstructionLoader.InstructionResult;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class OperatorPanel extends JPanel {

    private @NotNull InstructionNode instructionNode;
    private OnInstructionExecutedListener onInstructionExecutedListener;

    public OperatorPanel(@NotNull InstructionNode instructionNode, OnInstructionExecutedListener onExecuteBtnClickListener) {
        super();
        this.instructionNode = instructionNode;
        this.onInstructionExecutedListener = onExecuteBtnClickListener;
        initPanel();
    }

    private void initPanel() {
        setLayout(new BorderLayout());
        initTopTextArea();
        initCenterCodesTextArea();
        initBottomButtonArea();
    }

    private void initTopTextArea() {
        StringBuilder stringBuilder = new StringBuilder(instructionNode.name);
        if (instructionNode.description != null && instructionNode.description.length() != 0) {
            stringBuilder.append("\n");
            stringBuilder.append(instructionNode.description);
        }
        JTextArea topTextArea = new JTextArea(stringBuilder.toString());
        add(topTextArea, BorderLayout.NORTH);
    }

    private void initCenterCodesTextArea() {
        StringBuilder stringBuilder = new StringBuilder("------------------");
        for (String code : instructionNode.codes) {
            if (stringBuilder.length() != 0) {
                stringBuilder.append("\n");
            }
            stringBuilder.append(code);
        }
        JTextArea centerCodesTextArea = new JTextArea(stringBuilder.toString());
        add(centerCodesTextArea, BorderLayout.CENTER);
    }

    private void initBottomButtonArea() {
        JButton button = new JButton("Execute");
        button.addActionListener(e -> {
            List<InstructionResult> result = executeInstruction();
            System.out.println("result = "+result);
            if (onInstructionExecutedListener != null) {
                onInstructionExecutedListener.onExecuted(result);
            }
        });
        add(button, BorderLayout.SOUTH);
    }

    private List<InstructionResult> executeInstruction() {
        List<InstructionResult> instructionResultList = new ArrayList<>(instructionNode.codes.length);
        for (String instruction : instructionNode.codes) {
            InstructionResult instructionResult = AdbExecutor.executeInstruction(instruction);
            System.out.println("executeInstruction result = "+instructionResult);
            instructionResultList.add(instructionResult);
        }
        return instructionResultList;
    }

    public void setOnInstructionExecutedListener(OnInstructionExecutedListener onInstructionExecutedListener) {
        this.onInstructionExecutedListener = onInstructionExecutedListener;
    }

    interface OnInstructionExecutedListener {
        void onExecuted(List<InstructionResult> instructionResults);
    }

}
