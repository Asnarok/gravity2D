package gravity.simulation.display.random;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import gravity.simulation.Simulator;

public class RandomEntitiesListener implements ItemListener, ActionListener {

	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getSource() == RandomEntities.speed) {
			RandomEntities.minSpeed.setEnabled(!RandomEntities.minSpeed.isEnabled());
			RandomEntities.maxSpeed.setEnabled(!RandomEntities.maxSpeed.isEnabled());
		}else if(e.getSource() == RandomEntities.freeSpacing) {
			RandomEntities.minSpacing.setEnabled(!RandomEntities.minSpacing.isEnabled());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == RandomEntities.validate) {
			RandomEntities.generate(RandomEntities.freeSpacing.isSelected(), RandomEntities.speed.isSelected());
			Simulator.randomEntities.setVisible(false);
		}
	}

}
