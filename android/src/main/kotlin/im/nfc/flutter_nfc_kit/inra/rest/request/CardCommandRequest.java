package im.nfc.flutter_nfc_kit.inra.rest.request;

import java.io.Serializable;

public class CardCommandRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String description;
    private String command;
    private boolean ignoreFailure;

    public CardCommandRequest() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCommand() {
        return this.command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String toString() {
        StringBuffer result = (new StringBuffer("          [")).append(this.id);
        if (this.description != null && !this.description.trim().equals("") && !this.description.trim().equalsIgnoreCase("null")) {
            result.append(":").append(this.description == null ? "" : this.description);
        }

        result.append("]=>").append(this.command);
        result.append(", ignoreFailure=").append(this.ignoreFailure);
        return result.toString();
    }

    public boolean isIgnoreFailure() {
        return this.ignoreFailure;
    }

    public void setIgnoreFailure(boolean ignoreFailure) {
        this.ignoreFailure = ignoreFailure;
    }
}
