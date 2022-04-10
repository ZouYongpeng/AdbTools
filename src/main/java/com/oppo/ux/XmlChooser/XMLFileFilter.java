package com.oppo.ux.XmlChooser;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class XMLFileFilter extends FileFilter {

    @Override
    public boolean accept(File f) {
        String fileName = f.getName();
        return fileName.toLowerCase().endsWith(".xml");
    }

    @Override
    public String getDescription() {
        return "*.xml";
    }
}
