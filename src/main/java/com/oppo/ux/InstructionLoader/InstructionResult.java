package com.oppo.ux.InstructionLoader;

public class InstructionResult {

    public String instructionCode;
    public StringBuilder normalMessage;
    public StringBuilder errorMessage;

    public InstructionResult(String instructionCode, StringBuilder normalMessage, StringBuilder errorMessage) {
        this.instructionCode = instructionCode;
        this.normalMessage = normalMessage;
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "InstructionResult:" +
                "\n -- normalMessage : "+normalMessage+
                "\n -- errorMessage  : "+errorMessage;
    }
}
