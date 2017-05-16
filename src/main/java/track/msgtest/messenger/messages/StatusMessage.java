package track.msgtest.messenger.messages;

import java.util.Objects;

public class StatusMessage extends Message {
    private String text;

    public StatusMessage(String text) {
        type = Type.MSG_STATUS;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        if (!super.equals(other)) {
            return false;
        }
        StatusMessage message = (StatusMessage) other;
        return Objects.equals(text, message.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), text);
    }

    @Override
    public String toString() {
        return "StatusMessage{" +
                "text='" + text + '\'' +
                '}';
    }
}