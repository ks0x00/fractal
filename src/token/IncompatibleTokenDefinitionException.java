package token;

@SuppressWarnings("serial")
public class IncompatibleTokenDefinitionException extends RuntimeException {
	public IncompatibleTokenDefinitionException() {
		super("Incompatible Token Definition");
	}

	public IncompatibleTokenDefinitionException(char c) {
		super("Incompatible Token Definition Using Character" + c);
	}
}
