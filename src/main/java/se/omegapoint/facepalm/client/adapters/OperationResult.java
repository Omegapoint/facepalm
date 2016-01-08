package se.omegapoint.facepalm.client.adapters;

public class OperationResult {
    private final String message;
    private final boolean success;

    private OperationResult(final boolean success, final String message) {
        this.message = message;
        this.success = success;
    }

    public static OperationResult success() {
        return new OperationResult(true, null);
    }

    public static OperationResult failure(final String message) {
        return new OperationResult(false, message);
    }

    public String message() {
        if (success) {
            throw new IllegalStateException("No message available for successful operations");
        }
        return message;
    }

    public boolean isSuccess() {
        return success;
    }
}
