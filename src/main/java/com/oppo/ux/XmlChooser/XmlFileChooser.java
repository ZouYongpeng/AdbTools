package com.oppo.ux.XmlChooser;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;

public class XmlFileChooser extends JFileChooser {

    private OnChosenListener mOnChosenListener;

    public XmlFileChooser() {
        super();
        initFilter();
    }

    public XmlFileChooser(String currentDirectoryPath) {
        super(currentDirectoryPath);
        initFilter();
    }

    public XmlFileChooser(File currentDirectory) {
        super(currentDirectory);
        initFilter();
    }

    public XmlFileChooser(FileSystemView fsv) {
        super(fsv);
        initFilter();
    }

    public XmlFileChooser(File currentDirectory, FileSystemView fsv) {
        super(currentDirectory, fsv);
        initFilter();
    }

    public XmlFileChooser(String currentDirectoryPath, FileSystemView fsv) {
        super(currentDirectoryPath, fsv);
        initFilter();
    }

    private void initFilter() {
        XMLFileFilter xmlFileFilter = new XMLFileFilter();
        addChoosableFileFilter(xmlFileFilter);
        setFileFilter(xmlFileFilter);
    }

    @Override
    public int showOpenDialog(Component parent) throws HeadlessException {
        int val = super.showOpenDialog(parent);
        if (mOnChosenListener != null) {
            mOnChosenListener.onChosen(val == APPROVE_OPTION);
        }
        return val;
    }

    public void setOnChosenListener(OnChosenListener onChosenListener) {
        mOnChosenListener = onChosenListener;
    }

    public interface OnChosenListener {
        void onChosen(boolean isOk);
    }

}
