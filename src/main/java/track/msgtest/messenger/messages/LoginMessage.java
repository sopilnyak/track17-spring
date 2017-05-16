package track.msgtest.messenger.messages;

import java.util.Objects;

/**
 *
 */
public class LoginMessage extends Message {
    private String name;
    private String pass;

    public LoginMessage(String name, String pass) {
        type = Type.MSG_LOGIN;
        this.name = name;
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public boolean equals(Object other) {
        if (!super.equals(other)) {
            return false;
        }
        LoginMessage message = (LoginMessage) other;
        return Objects.equals(name, message.getName()) &&
                Objects.equals(pass, message.getPass());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }

    @Override
    public String toString() {
        return "LoginMessage{" +
                "name='" + name + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }
}
