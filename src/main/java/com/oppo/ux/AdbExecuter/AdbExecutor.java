package com.oppo.ux.AdbExecuter;

import com.oppo.ux.InstructionLoader.InstructionResult;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AdbExecutor {

    public static InstructionResult executeInstruction(String instruction) {
        BufferedReader inputReader = null;
        BufferedReader errorReader = null;
        StringBuilder returnString = new StringBuilder();
        StringBuilder errorString = new StringBuilder();

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(instruction.split(" "));
            Process process = processBuilder.start();
            //脚本执行输出信息
            inputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String str;
            while ((str = inputReader.readLine()) != null) {
                if (str.length() != 0) {
                    if (returnString.length() != 0) {
                        returnString.append("\n");
                    }
                    returnString.append(str);
                }
            }
            //脚本执行异常时的输出信息
//            errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
//            String error;
//            while ((error = errorReader.readLine()) != null) {
//                if (error.length() != 0) {
//                    if (errorString.length() != 0) {
//                        errorString.append("\n");
//                    }
//                    errorString.append(error);
//                }
//            }
            try {
                if (0 != process.waitFor()) {
                    throw new Exception("Adb execute[adb devices] fail! ");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputReader != null) {
                    inputReader.close();
                }
//                if (errorReader != null) {
//                    errorReader.close();
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new InstructionResult(instruction, returnString, errorString);
    }

}
