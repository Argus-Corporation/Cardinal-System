package net.argus.util.notify;

public class DefaultNotify extends Notify {

	public DefaultNotify(String iconPath) {
		super(new DefaultNotifyComponent(), iconPath);
	}

}
