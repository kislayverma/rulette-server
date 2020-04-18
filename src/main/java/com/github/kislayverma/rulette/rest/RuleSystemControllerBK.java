package com.github.kislayverma.rulette.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RuleSystemControllerBK {

//    private final RuleSystemFactory ruleSystemFactory;
    private static final Logger LOGGER = LoggerFactory.getLogger(RuleSystemControllerBK.class);

//    public RuleSystemControllerBK() {
//        this.ruleSystemFactory = new RuleSystemFactory();
//    }

//    public List<Rule> getAllRules(Request request, Response response) {
//        String ruleSystemName = request.getHeader(Constants.Url.RULE_SYSTEM_NAME);
//        return getRuleSystem(ruleSystemName).getAllRules();
//    }
//
//    public Rule getApplicableRule(Request request, Response response) throws Exception {
//        try {
//            Map<String, String> map = convertInputJsonToMap(request);
//            String ruleSystemName = request.getHeader(Constants.Url.RULE_SYSTEM_NAME);
//
//            return getRuleSystem(ruleSystemName).getRule(map);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            response.setException(ex);
//            response.setResponseStatus(HttpResponseStatus.BAD_REQUEST);
//        }
//
//        return null;
//    }
//
//    public Rule getNextApplicableRule(Request request, Response response) throws Exception {
//        try {
//            Map<String, String> map = convertInputJsonToMap(request);
//            String ruleSystemName = request.getHeader(Constants.Url.RULE_SYSTEM_NAME);
//
//            return getRuleSystem(ruleSystemName).getNextApplicableRule(map);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            response.setException(ex);
//            response.setResponseStatus(HttpResponseStatus.BAD_REQUEST);
//        }
//
//        return null;
//    }
//
//    public Rule getRule(Request request, Response response) {
//        String ruleSystemName = request.getHeader(Constants.Url.RULE_SYSTEM_NAME);
//        String ruleIdStr = request.getHeader(Constants.Url.RULE_ID);
//
//        return getRuleSystem(ruleSystemName).getRule(ruleIdStr);
//    }
//
//    public Rule addRule(Request request, Response response) throws Exception {
//        try {
//            Map<String, String> map = convertInputJsonToMap(request);
//            String ruleSystemName = request.getHeader(Constants.Url.RULE_SYSTEM_NAME);
//
//            return getRuleSystem(ruleSystemName).addRule(map);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            response.setException(ex);
//            response.setResponseStatus(HttpResponseStatus.BAD_REQUEST);
//        }
//
//        return null;
//    }
//
//    public Rule updateRule(Request request, Response response) throws Exception {
//        try {
//            Map<String, String> map = convertInputJsonToMap(request);
//            String ruleSystemName = request.getHeader(Constants.Url.RULE_SYSTEM_NAME);
//            String ruleIdStr = request.getHeader(Constants.Url.RULE_ID);
//            Integer ruleId = Integer.parseInt(ruleIdStr);
//
//            RuleSystem rs = getRuleSystem(ruleSystemName);
//            Rule oldRule = rs.getRule(ruleId);
//            if (oldRule == null) {
//                throw new RuntimeException("No existing rule found for the input");
//            } else {
//                Rule newRule = rs.createRuleObject(map);
//
//                return rs.updateRule(oldRule, newRule);
//            }
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            response.setException(ex);
//            response.setResponseStatus(HttpResponseStatus.BAD_REQUEST);
//        }
//
//        return null;
//    }
//
//    public void deleteRule(Request request, Response response) throws Exception {
//        try {
//            Map<String, String> map = convertInputJsonToMap(request);
//            String ruleSystemName = request.getHeader(Constants.Url.RULE_SYSTEM_NAME);
//            String ruleIdStr = request.getHeader(Constants.Url.RULE_ID);
//
//            getRuleSystem(ruleSystemName).deleteRule(ruleIdStr);
//            response.setResponseNoContent();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            response.setException(ex);
//            response.setResponseStatus(HttpResponseStatus.BAD_REQUEST);
//        }
//    }
//
//    public void reloadRuleSystem(Request request, Response response) throws Exception {
//        String ruleSystemName = request.getHeader(Constants.Url.RULE_SYSTEM_NAME);
//        ruleSystemFactory.reloadRuleSystem(ruleSystemName);
//        response.setResponseNoContent();
//    }
//
//    private RuleSystem getRuleSystem(String ruleSystemName) {
//        return ruleSystemFactory.getRuleSystem(ruleSystemName);
//    }
//
//    private Map<String, String> convertInputJsonToMap(Request request) throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        LOGGER.info("Request headers : " + request.getHeaderNames());
//        LOGGER.info("Request body  : " + request.getBodyFromUrlFormEncoded(true));
//
//        Map<String, String> map = new HashMap<>();
//        Set<String> keySet = request.getBodyFromUrlFormEncoded(true).keySet();
//        for (String payload : keySet) {
//            Map<String, Object> objMap =
//                mapper.readValue(payload, new TypeReference<Map<String, Object>>() {});
//
//            objMap.entrySet().stream().forEach((entry) -> {
//                map.put(entry.getKey(), (String) entry.getValue());
//            });
//
//            return map;
//        }
//
//        return null;
//    }
}
