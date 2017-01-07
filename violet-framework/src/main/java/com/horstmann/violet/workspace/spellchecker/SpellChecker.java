package com.horstmann.violet.workspace.spellchecker;

import java.io.IOException;
import java.util.List;
import org.languagetool.JLanguageTool;
import org.languagetool.language.BritishEnglish;
import org.languagetool.rules.Rule;
import org.languagetool.rules.RuleMatch;

/**
 * This class check correctness of string
 *
 * @author Sebastian Podgorski
 */
public class SpellChecker {

    private SpellChecker(){}

    public static boolean isCorrectWord(final String word) throws IOException {
        JLanguageTool langTool = new JLanguageTool(new BritishEnglish());
        for (Rule rule : langTool.getAllRules()) {
            if (!rule.isDictionaryBasedSpellingRule()) {
                langTool.disableRule(rule.getId());
            }
        }

        List<RuleMatch> matches = langTool.check(word);
        int numberOfErrors = matches.size();
        return numberOfErrors == 0;
    }
}
