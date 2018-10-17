package phamf.com.chemicalapp.Abstraction.SpecialDataType;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class IsomerismType {

    public static final String CARBON_CHAIN_ISOMERISM = "Đồng phân mạch cacbon";

    public static final String GEOMETRIC_ISOMERISM = "Đồng phân hình học";

    @StringDef({CARBON_CHAIN_ISOMERISM, GEOMETRIC_ISOMERISM})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {}

}
