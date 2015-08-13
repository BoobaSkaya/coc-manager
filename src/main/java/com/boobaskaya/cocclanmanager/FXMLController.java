package com.boobaskaya.cocclanmanager;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;

import com.boobaskaya.cocclanmanager.model.Building;
import com.boobaskaya.cocclanmanager.model.BuildingFactory;
import com.boobaskaya.cocclanmanager.model.BuildingType;
import com.boobaskaya.cocclanmanager.model.Clan;
import com.boobaskaya.cocclanmanager.model.Player;
import com.boobaskaya.cocclanmanager.model.TownHall;
import com.boobaskaya.cocclanmanager.tools.JAXBTools;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

public class FXMLController implements Initializable {

	private static final Logger LOGGER = Logger.getLogger("COC");

	private static File clanFile = new File("clan.xml");

    private Clan clan;

    @FXML
	private BorderPane mainBorderPane;
	@FXML
    private TableView<Player> memberTable;
    @FXML
    private TableColumn<Player, String> pseudoColumn;
    @FXML
	private TableColumn<Player, Integer> TownHallColumn;
    @FXML
	private TableColumn<Building, Integer> levelColumn;
	@FXML
    private TableView<Building> buildingTable;
    @FXML
    private ComboBox<Player> cbMember;
    @FXML
	private ComboBox<Integer> cbTownHall;
    @FXML
    private ComboBox<BuildingType> cbBuilding;
    @FXML
    private ComboBox<Integer> cbLevel;
	@FXML
	private PieChart thPieChart;
	@FXML
	private TabPane mainTabPane;
	@FXML
	private Tab memberTab;

	private ObservableList<Data> thPieChartData;
	@FXML
	private PieChart buildingsPieChart;

	private ObservableList<Data> buildingsPieChartData;

	private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Clan page
        memberTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        memberTable.setEditable(true);
        pseudoColumn.setCellFactory(TextFieldTableCell.<Player>forTableColumn());
        pseudoColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Player, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Player, String> event) {
                System.out.println("New pseudo for player : " + event.getNewValue());
                event.getRowValue().setPseudo(event.getNewValue());
            }
        });
		//////////////
        // Member page
		//////////////
		buildingTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		buildingTable.setEditable(true);
		levelColumn.setEditable(true);
		levelColumn.setCellFactory(ComboBoxTableCell
				.<Building, Integer> forTableColumn(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13 }));
		levelColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Building, Integer>>() {

			@Override
			public void handle(CellEditEvent<Building, Integer> event) {
				LOGGER.info("CellEdit: New level for building " + event.getRowValue() + " : " + event.getNewValue());
				event.getRowValue().setLevel(event.getNewValue());

			}
		});

        cbBuilding.setItems(FXCollections.observableArrayList(BuildingType.values()));
        cbBuilding.setValue(BuildingType.CANNON);
		cbLevel.setItems(
				FXCollections.observableArrayList(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13 }));

        cbLevel.setValue(1);
		// buttonCell permit to customize the combox box cell when displayed
		cbMember.setButtonCell(new ListCell<Player>() {
			@Override
			public void updateItem(Player item, boolean empty) {
				super.updateItem(item, empty);
				if (item != null) {
					setText(item.getPseudo());
				}
			}
		});
		// cellFactory permits to customize combobox cells when dropped down
		cbMember.setCellFactory(new Callback<ListView<Player>, ListCell<Player>>() {
			@Override
			public ListCell<Player> call(ListView<Player> arg0) {
				ListCell<Player> cells = new ListCell<Player>() {
					@Override
					public void updateItem(Player item, boolean empty) {
						super.updateItem(item, empty);
						if (item != null) {
							setText(item.getPseudo());
						}
					}
				};
				return cells;
			}
		});
		cbTownHall.setItems(FXCollections.observableArrayList(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }));

		// Stats
		thPieChartData = FXCollections.observableArrayList();
		thPieChart.setData(thPieChartData);

		buildingsPieChartData = FXCollections.observableArrayList();
		buildingsPieChart.setData(buildingsPieChartData);
		// load and set initial datas
		reloadFile();
    }

    private void reloadFile() {
        if (clanFile.exists()) {
            try {
                setClan(JAXBTools.fromFile(clanFile, Clan.class));
            } catch (JAXBException ex) {
                Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
		} else {
			// no file, create default clan
			setClan(new Clan());
        }
    }

    private void setClan(Clan clan) {
        this.clan = clan;
        memberTable.setItems(clan.getMembers());
        cbMember.setItems(clan.getMembers());
        //simulate cbMember action
        if (clan.getMembers().size() > 0) {
            cbMember.setValue(clan.getMembers().get(0));
            this.cbMemberAction(null);
        }
		updateStats();
    }

	private void updateStats() {
		// Compute TH stats
		final int[] thNumber = new int[new TownHall().getMaxLevel(0)+1];
		Arrays.fill(thNumber, 0);
		// parse player and count th levels
		clan.getMembers().stream().forEach(p -> thNumber[p.getTownHall()]++);

		thPieChartData.clear();
		for (int i = 0; i < thNumber.length; i++) {
			if (thNumber[i] > 0) {
				Data newData = new Data("TH" + i, thNumber[i]);
				thPieChartData.add(newData);
			}
		}
		// Compute buildings stats
		final int[] buildingsNumber = new int[BuildingType.values().length];
		Arrays.fill(buildingsNumber, 0);
		// parse player and count the building type
		clan.getMembers().stream()
				.forEach(p -> p.getBuildings().stream().forEach(b -> buildingsNumber[b.getType().ordinal()]++));

		buildingsPieChartData.clear();
		for (int i = 0; i < buildingsNumber.length; i++) {
			if (buildingsNumber[i] > 0) {
				Data newData = new Data(BuildingType.values()[i].toString(), buildingsNumber[i]);
				buildingsPieChartData.add(newData);
			}
		}
	}

	@FXML
    private void addMember(ActionEvent event) {
        clan.getMembers().add(new Player());
		updateStats();
    }

	@FXML
	private void cloneMember(ActionEvent event) {
		// Clone the selected member
		Player selectedPlayer = memberTable.getSelectionModel().getSelectedItem();
		if (selectedPlayer != null) {
			Player clonedPlayer = selectedPlayer.clone();
			clonedPlayer.setPseudo(clonedPlayer.getPseudo() + "_clone");
			clan.getMembers().add(clonedPlayer);
		}
		updateStats();
	}

    @FXML
    private void rmMember(ActionEvent event) {
        //retrieve the index of the item to remove
    	ArrayList<Player> toBeRemoved = new ArrayList<>();
    	toBeRemoved.addAll(memberTable.getSelectionModel().getSelectedItems());
		toBeRemoved.stream().forEach((p) -> {
            clan.getMembers().remove(p);
        });
		updateStats();
    }

    @FXML
    private void editMemberAction(ActionEvent event) {
        //select the member in the combobox
    	cbMember.setValue(memberTable.getSelectionModel().getSelectedItem());
    	//and switch to tab
    	mainTabPane.getSelectionModel().select(memberTab);
    }

    @FXML
	private void openAction(ActionEvent event) {
		FileChooser fc = new FileChooser();
		fc.setTitle("Open clan file");
		fc.setInitialDirectory(clanFile.getParentFile());
		File f = fc.showOpenDialog(stage);
		if (f != null) {
			clanFile = f;
			updateWindowTitle();
			reloadFile();
		}
    }

    @FXML
    private void saveAction(ActionEvent event) {
        save();
    }

	@FXML
	private void saveAsAction(ActionEvent event) {
		// ask file to save to
		FileChooser fc = new FileChooser();
		fc.setTitle("Save clan to file");
		fc.setInitialDirectory(clanFile.getParentFile());
		File f = fc.showSaveDialog(stage);
		if (f != null) {
			clanFile = f;
			updateWindowTitle();
			save();
		}
	}

	private void save() {
		try {
			LOGGER.info("Save clan to " + clanFile.getAbsolutePath());
			JAXBTools.toFile(this.clan, Clan.class, clanFile);
		} catch (JAXBException ex) {
			Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@FXML
	private void quitAction(ActionEvent event) {
		// close ALL !!
		LOGGER.info("Quit action");
		System.exit(0);
	}

    @FXML
    private void cbMemberAction(ActionEvent event) {
        System.out.println("Member " + cbMember.getValue() + " selected");
        Player selectedPlayer = cbMember.getValue();
		if (selectedPlayer != null) {
			buildingTable.setItems(selectedPlayer.getBuildings());
			cbTownHall.setValue(selectedPlayer.getTownHall());
		}
    }

    @FXML
	private void cbTownHallAction(ActionEvent event) {
		LOGGER.info("cbHdv action to " + cbTownHall.getValue());
		cbMember.getValue().setTownHall(cbTownHall.getValue());
		updateStats();
    }

    @FXML
    private void cbBuildingAction(ActionEvent event) {
        System.out.println("Building selected");
        //change accessible levels?
    }

    @FXML
    private void cbLevelAction(ActionEvent event) {
        //nothing to do
    }

    @FXML
	private void addBuilding(ActionEvent event) {
		Building newBuilding = BuildingFactory.createBuilding(cbBuilding.getValue());
		if (newBuilding != null) {
			newBuilding.setLevel(cbLevel.getValue());
			cbMember.getValue().addBuilding(newBuilding);
			updateStats();
		}
	}

    @FXML
	private void addMaxBuilding(ActionEvent event) {
		cbMember.getValue().getBuildings().clear();
		cbMember.getValue().getBuildings().addAll(BuildingFactory.getMax(cbMember.getValue().getTownHall()));
		updateStats();

	}

    @FXML
    private void rmBuilding(ActionEvent event) {
		// get the selected Player
		Player player = cbMember.getValue();
		// get the selected buildings
		ArrayList<Building> toBeRemoved = new ArrayList<>();
		toBeRemoved.addAll(buildingTable.getSelectionModel().getSelectedItems());
		toBeRemoved.stream().forEach(p -> player.getBuildings().remove(p));
		updateStats();
    }

	@FXML
	private void aboutAction(ActionEvent event) {
		// show about dialog
		String resource = "/fxml/aboutDialog.fxml";
		try {
			// create a new stage for the new dialog
			Stage stage2 = new Stage();
			// load the new dialog
			FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
			Parent page = loader.load();
			AboutDialogController controller = loader.getController();
			controller.setStage(stage2);

			Scene scene = new Scene(page);
			stage2.setScene(scene);
			stage2.showAndWait();
		} catch (IOException e) {
			LOGGER.severe("Failed to load resource " + resource);
			e.printStackTrace();
		}
	}

	public void setStage(Stage stage) {
		this.stage = stage;
		updateWindowTitle();
	}

	private void updateWindowTitle() {
		if (clanFile != null && stage != null) {
			stage.setTitle(clanFile.getName());
		}
	}
}
