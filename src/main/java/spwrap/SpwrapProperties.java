package spwrap;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @see <a href="https://github.com/mhewedy/spwrap/wiki/Configurations">spwrap configurations</a>
 */
@ConfigurationProperties(prefix = "spwrap")
public class SpwrapProperties {

    private boolean enabled = true;

    /**
     * Every Stored procedure that is being invoked by spwrap might have the last 2 parameters
     * as output parameters of type SMALLINT and VARCHAR to represent the stored procedure status code and message
     * respectively. You can turn this feature on by setting this property value to true.
     * (You never deal with these two parameters when implementing {@link spwrap.mappers.TypedOutputParamMapper})
     */
    private boolean useStatusFields = false;

    /**
     * As mentioned in the description of useStatusFields, that every stored procedure by default need to provide 2
     * additional output parameters at the last of parameter list to represent stored procedure status code and
     * optional message. by default the code 0 means success, so if your stored procedure returns values other than 0,
     * then a CallException will be thrown by spwrap, however if you just want to change the success value,
     * then use this property successCode.
     * To enable this property, first enable {@link #useStatusFields}
     */
    private short successCode = 0;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isUseStatusFields() {
        return useStatusFields;
    }

    public void setUseStatusFields(boolean useStatusFields) {
        this.useStatusFields = useStatusFields;
    }

    public short getSuccessCode() {
        return successCode;
    }

    public void setSuccessCode(short successCode) {
        this.successCode = successCode;
    }
}
