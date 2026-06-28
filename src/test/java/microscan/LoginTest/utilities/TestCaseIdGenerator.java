package utilities;

import java.util.HashMap;
import java.util.Map;

public class TestCaseIdGenerator {

    private static final Map<String, Integer> counters = new HashMap<>();

    private static final Map<String, String> prefixes = new HashMap<>();

    static {

        prefixes.put("Login", "LOGIN");
        prefixes.put("ForgotPassword", "FP");
        prefixes.put("OTP", "OTP");
        prefixes.put("ResetPassword", "RESET");
        prefixes.put("Dashboard", "DASH");
        prefixes.put("Logout", "LOGOUT");

    }

    public static synchronized String generate(String module) {

        String prefix = prefixes.getOrDefault(module, module.toUpperCase());

        int count = counters.getOrDefault(prefix, 0) + 1;

        counters.put(prefix, count);

//        return prefix + "-" + String.format("%03d", count);
        return prefix.trim() + "-" + String.format("%03d", count);

    }

}