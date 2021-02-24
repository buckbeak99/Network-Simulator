package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

public class ProjectController {

	@FXML
	private TextField areaSize;

	@FXML
	private TextField cellRadius;

	@FXML
	private TextField channelTraffic;

	@FXML
	private TextField frequencyReuse;

	@FXML
	private RadioButton radioButtonMacroCell;

	@FXML
	private RadioButton radioButtonMicroCell;

	@FXML
	private TextField totalCellNumber;

	@FXML
	private TextField totalChannelNumber;

	@FXML
	private TextField totalChannelCapacity;

	@FXML
	private TextField totalConcurrentCall;

	@FXML
	private Button buttorCalculate;

	@FXML
	private TextField transmittingHeight;

	@FXML
	private TextField carrierFrequency;

	@FXML
	private TextField receivingHeight;

	@FXML
	private TextField propagationDistance;

	@FXML
	private RadioButton radioButtonSmall;

	@FXML
	private RadioButton radioButtonMedium;

	@FXML
	private RadioButton radioButtonLarge;

	@FXML
	private RadioButton radioButtonUrban;

	@FXML
	private RadioButton radioButtonSuburban;

	@FXML
	private RadioButton radioButtonOpen;

	@FXML
	private TextField pathLoss;

	@FXML
	private ToggleGroup cell;

	@FXML
	private Button buttonCalculate2;

	@FXML
	private ToggleGroup city;

	@FXML
	private ToggleGroup area;

	private String areaValue = "", citySizeValue = "";

	@FXML
	void calculate(ActionEvent event) {
		try {
			Double areaSizeValue = Double.parseDouble(areaSize.getText());
			Double cellRadiusValue = Double.parseDouble(cellRadius.getText());
			Double trafficValue = Double.parseDouble(channelTraffic.getText());
			Double frequencyReuseValue = Double.parseDouble(frequencyReuse.getText());
			Double totalCellNumberValue = areaSizeValue / (1.5 * Math.sqrt(3) * (cellRadiusValue * cellRadiusValue));
			Double totalChannelNumberValue = (trafficValue / frequencyReuseValue);
			Double totalChannelCapacityValue = (totalChannelNumberValue * totalCellNumberValue);
			Double totalConcurrentCallValue = totalChannelCapacityValue;

			areaSize.clear();
			cellRadius.clear();
			channelTraffic.clear();
			frequencyReuse.clear();

			setResultcell(totalCellNumberValue);
			setResultchannel(totalChannelNumberValue);
			setResultcapacity(totalChannelCapacityValue);
			setResultcall(totalConcurrentCallValue);

		} catch (Exception e) {

		}
	}

	void setResultcell(Double totalCellNumberValue) {
		totalCellNumber.setText(totalCellNumberValue.toString());
	}

	void setResultchannel(Double totalChannelNumberValue) {
		totalChannelNumber.setText(totalChannelNumberValue.toString());
	}

	void setResultcapacity(Double totalChannelCapacityValue) {
		totalChannelCapacity.setText(totalChannelCapacityValue.toString());
	}

	void setResultcall(Double totalConcurrentCallValue) {
		totalConcurrentCall.setText(totalConcurrentCallValue.toString());
	}

	public void initializeArea(URL url, ResourceBundle rb) {
		area = new ToggleGroup();
		this.radioButtonUrban.setToggleGroup(area);
		this.radioButtonSuburban.setToggleGroup(area);
		this.radioButtonOpen.setToggleGroup(area);
		

	}

	public void initializeCity(URL url, ResourceBundle rb) {
		city = new ToggleGroup();
		this.radioButtonSmall.setToggleGroup(city);
		this.radioButtonMedium.setToggleGroup(city);
		this.radioButtonLarge.setToggleGroup(city);

	}

	@FXML
	void lossCalculate(ActionEvent event) {
		Double carrierFrequencyValue = Double.parseDouble(carrierFrequency.getText());
		
		pathLoss.clear();
		
		

		Double transmittingHeightValue = Double.parseDouble(transmittingHeight.getText());
		Double receivingHeightValue = Double.parseDouble(receivingHeight.getText());
		Double propagationDistanceValue = Double.parseDouble(propagationDistance.getText());
		
		
		// equation for small and medium urban
		Double correctionFactorValue = ((1.1 * Math.log10(carrierFrequencyValue)) - 0.7) * receivingHeightValue
				- (1.56 * Math.log10(carrierFrequencyValue) - 0.8);
		Double pathLossUrban = 69.55 +( 26.16 * Math.log10(carrierFrequencyValue))
				- (13.82 * Math.log10(transmittingHeightValue)) - correctionFactorValue
				+ (44.9 - 6.55 * Math.log10(transmittingHeightValue)) * Math.log10(propagationDistanceValue);

		// equation for large urban
		// fc<= 300
		Double correctionFactorValueLarge = (8.29 * (Math.log10(1.54) * receivingHeightValue)* (Math.log10(1.54) * receivingHeightValue))- 1.1;
		Double pathLossUrbanLarge = 69.55 + 26.16 * Math.log10(carrierFrequencyValue)- 13.82 * Math.log10(transmittingHeightValue) - correctionFactorValueLarge * (receivingHeightValue)+ (44.9 - 6.55 * Math.log10(transmittingHeightValue)) * Math.log10(propagationDistanceValue);
		// fc>=300
		Double correctionFactorValueLargefc = (3.2 * ((Math.log10(11.75) * receivingHeightValue))* (Math.log10(11.75) * receivingHeightValue)) - 4.97;
		Double pathLossUrbanLargefc = 69.55 + 26.16 * Math.log10(carrierFrequencyValue)- 13.82 * Math.log10(transmittingHeightValue) - correctionFactorValueLargefc * (receivingHeightValue)+ (44.9 - 6.55 * Math.log10(transmittingHeightValue)) * Math.log10(propagationDistanceValue);
		// eqn for suburban

		Double pathLossSubUrban = pathLossUrban
				- 2 * ((Math.log10(carrierFrequencyValue) / 28) * (Math.log10(carrierFrequencyValue) / 28)) - 5.4;
		// eqn for open
		Double pathLossOpen = pathLossUrban
				- 4.78 * ((Math.log10(carrierFrequencyValue)) * (Math.log10(carrierFrequencyValue)))
				- 18.733 * Math.log10(carrierFrequencyValue) - 40.98;

		String citySizeValue = "", areaValue = "";
		if (radioButtonSmall.isSelected()) {
			citySizeValue = radioButtonSmall.getText().toString();
			
		}
		else if (radioButtonMedium.isSelected()) {
			citySizeValue = radioButtonMedium.getText().toString();
			
		}else {
			citySizeValue = radioButtonLarge.getText().toString();
			
		}
		
		
		if (radioButtonUrban.isSelected()) {
			areaValue = radioButtonUrban.getText().toString();
		
		} else if (radioButtonSuburban.isSelected()) {
			areaValue = radioButtonSuburban.getText().toString();
		
		} else {
			areaValue = radioButtonOpen.getText().toString();
		
		}
		
		String city = citySizeValue;
		String area = areaValue;

		if (city.equals("Small") || city.equals("Medium")) {
			if (area.equals("Urban")) {
				pathLoss.setText(pathLossUrban.toString());
			} else if (area.equals("SubUrban")) {
				pathLoss.setText(pathLossSubUrban.toString());
			} else
				pathLoss.setText(pathLossOpen.toString());
		} 
		
		
		 else if(city.equals("Large")) { 
			 if(carrierFrequencyValue <=300) {
				 pathLoss.setText(pathLossUrbanLarge.toString()); 
				 } 
			 else if (carrierFrequencyValue >=300) { 
				 pathLoss.setText(pathLossUrbanLargefc.toString()); 
				 } 
			 
				  
			 }
		 

		carrierFrequency.clear();
		transmittingHeight.clear();
		receivingHeight.clear();
		propagationDistance.clear();

	}

}
