//package com.horstmann.violet.product.diagram.classes.edge;
//
///**
// * Created by Wenaro on 2017-01-07.
// */
//public class SpellChecker {
//
//    public static void check(){
//        JLanguageTool langTool = new JLanguageTool(new BritishEnglish());
//        for (Rule rule : langTool.getAllRules()) {
//            if (!rule.isDictionaryBasedSpellingRule()) {
//                langTool.disableRule(rule.getId());
//            }
//        }
//        List<RuleMatch> matches = langTool.check("A speling error");
//        for (RuleMatch match : matches) {
//            System.out.println("Potential typo at characters " +
//                match.getFromPos() + "-" + match.getToPos() + ": " +
//                match.getMessage());
//            System.out.println("Suggested correction(s): " +
//                match.getSuggestedReplacements());
//        }
//    }
//}
