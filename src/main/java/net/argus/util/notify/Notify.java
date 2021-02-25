package net.argus.util.notify;

public abstract class Notify {
	
	private NotifyWindow window = new NotifyWindow();
	
	private NotifyComponent component;
	
	public Notify(NotifyComponent component) {
		this.component = component;
	}
	
	public void show(String title, String message) {
		component.setTitle(title);
		component.setIcon("C:\\Users\\Jean\\Documents\\Django\\Chat\\Project\\res\\favicon16x16.png");
		component.setMessage(message);
		window.setNotifyComponent(component);
		
		window.pack();
		window.setVisible(true);
		//System.out.println("packed");
	}
	
	public static void main(String[] args) {
		Notify notify = new DefaultNotify();
		
		notify.show("Pokémon go", "Bonjour les copains, moi je vais bien et vous? Je suis à Vernet. A bientôt, Django."/*" comment aller vous? rgskjn df:j gdf:k gjidkf hikdfhnjgbhdkgnfsgdfjhgdshgdfkdsbg fbg,ndf bgk s bgbfsbhfdgsf"*/);
	}
	
}
