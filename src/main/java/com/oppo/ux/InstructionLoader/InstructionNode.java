package com.oppo.ux.InstructionLoader;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class InstructionNode {

    public String name;
    public String description;
    public String[] codes;

    public InstructionNode(@NotNull String name, String description, @NotNull String code) {
        this.name = name;
        this.description = description;
        this.codes = toList(code);
    }

    private String[] toList(@NotNull String code) {
        code = code.trim();
        String[] codes = code.split("\\r?\\n");
        for (int i = 0; i < codes.length; i++) {
            codes[i] = codes[i].trim();
        }
        return codes;
    }

    @Override
    public String toString() {
        return "InstructionNode: " +
                "\n -- name = " + name +
                "\n -- description = " + description +
                "\n -- code = " + Arrays.toString(codes);
    }
}
