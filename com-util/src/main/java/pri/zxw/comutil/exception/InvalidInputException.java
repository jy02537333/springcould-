package pri.zxw.comutil.exception;


public class InvalidInputException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = -7882632216382045468L;

    private final String parameterName;

    private final String parameterType;


    /**
     * Constructor for MissingServletRequestParameterException.
     *
     * @param parameterName the name of the invalid parameter
     * @param parameterType the expected type of the invalid parameter
     */
    public InvalidInputException(String parameterName, String parameterType) {
        super();
        this.parameterName = parameterName;
        this.parameterType = parameterType;
    }


    public String getMessage() {
        return "Required " + this.parameterType + " parameter  is invalid  " + "," + this.parameterName;
    }

    /**
     * Return the name of the offending parameter.
     */
    public final String getParameterName() {
        return this.parameterName;
    }

    /**
     * Return the expected type of the offending parameter.
     */
    public final String getParameterType() {
        return this.parameterType;
    }

}
