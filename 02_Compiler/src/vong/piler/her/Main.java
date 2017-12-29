package vong.piler.her;

import java.io.File;

import vong.piler.her.generator.Generator;
import vong.piler.her.lexer.Lexer;
import vong.piler.her.parser.Parser;
import vong.piler.her.steakmachine.Steakmachine;

public class Main {
    public static void main(String[] args) {
        File sourceFile;
        Lexer lexer = new Lexer(sourceFile);
        Generator generator = new Generator();
        Parser parser = new Parser(lexer.lex());
        Steakmachine steakmachine = new Steakmachine(parser.parse());
    }
}
