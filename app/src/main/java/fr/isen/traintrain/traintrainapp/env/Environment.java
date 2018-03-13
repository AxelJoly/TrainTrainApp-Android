package fr.isen.traintrain.traintrainapp.env;

/**
 * Created by axel on 13/03/2018.
 */

public class Environment {
    private static String SNCF_API_ROOT = "https://api.sncf.com/v1/coverage/sncf/journeys?";
    private static String SCNF_API_TOKEN = "73e2ada2-5e01-4f45-a0f4-0b54577e32ff";

    public String getSNCF_API_ROOT() {
        return SNCF_API_ROOT;
    }

    public String getSCNF_API_TOKEN() {
        return SCNF_API_TOKEN;
    }
}
