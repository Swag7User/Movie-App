package com.UZH.MovieApp.client;

import java.util.Comparator;

import com.UZH.MovieApp.shared.Movie;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;

public class ResultTable extends Composite{
	
	static CellTable<Movie> movieTable;
	static CellTable<Movie> globalMovieTable;
	VerticalPanel tablePanel;

public VerticalPanel createTable(){
	// Create a CellTable.
	movieTable = new CellTable<Movie>();
	globalMovieTable = new CellTable<Movie>();

	// Create wikiid column.
	TextColumn<Movie> wikiidColumn = new TextColumn<Movie>() {
		@Override
		public String getValue(Movie movie) {
			return movie.wikiid;
		}
	};
	// Create freebaseid column.
	TextColumn<Movie> freebaseidColumn = new TextColumn<Movie>() {
		@Override
		public String getValue(Movie movie) {
			return movie.freebaseid;
		}
	};
	// Create name column.
	TextColumn<Movie> nameColumn = new TextColumn<Movie>() {
		@Override
		public String getValue(Movie movie) {
			return movie.name;
		}
	};
	// Create releasedate column.
	TextColumn<Movie> releasedateColumn = new TextColumn<Movie>() {
		@Override
		public String getValue(Movie movie) {
			return movie.releasedate;
		}
	};
	// Create boxoffice column.
	TextColumn<Movie> boxofficeColumn = new TextColumn<Movie>() {
		@Override
		public String getValue(Movie movie) {
			return movie.boxoffice;
		}
	};
	// Create runtime column.
	TextColumn<Movie> runtimeColumn = new TextColumn<Movie>() {
		@Override
		public String getValue(Movie movie) {
			return movie.runtime;
		}
	};
	// Create languages column.
	TextColumn<Movie> languagesColumn = new TextColumn<Movie>() {
		@Override
		public String getValue(Movie movie) {
			return movie.languages;
		}
	};
	// Create countries column.
	TextColumn<Movie> countriesColumn = new TextColumn<Movie>() {
		@Override
		public String getValue(Movie movie) {
			return movie.countries;
		}
	};
	// Create genres column.
	TextColumn<Movie> genresColumn = new TextColumn<Movie>() {
		@Override
		public String getValue(Movie movie) {
			return movie.genres;
		}
	};
	nameColumn.setSortable(true);
	wikiidColumn.setSortable(true);

	// Add the columns.
	movieTable.addColumn(wikiidColumn, "Wiki ID");
	movieTable.addColumn(freebaseidColumn, "Freebase ID");
	movieTable.addColumn(nameColumn, "Name");
	movieTable.addColumn(releasedateColumn, "Release Date");
	movieTable.addColumn(boxofficeColumn, "Boxoffice");
	movieTable.addColumn(runtimeColumn, "Runtime");
	movieTable.addColumn(languagesColumn, "Languages");
	movieTable.addColumn(countriesColumn, "Countries");
	movieTable.addColumn(genresColumn, "Genres");

	globalMovieTable.addColumn(wikiidColumn, "Wiki ID");
	globalMovieTable.addColumn(freebaseidColumn, "Freebase ID");
	globalMovieTable.addColumn(nameColumn, "Name");
	globalMovieTable.addColumn(releasedateColumn, "Release Date");
	globalMovieTable.addColumn(boxofficeColumn, "Boxoffice");
	globalMovieTable.addColumn(runtimeColumn, "Runtime");
	globalMovieTable.addColumn(languagesColumn, "Languages");
	globalMovieTable.addColumn(countriesColumn, "Countries");
	globalMovieTable.addColumn(genresColumn, "Genres");

	// Create a data provider.
	ListDataProvider<Movie> dataProvider = new ListDataProvider<>();
	ListDataProvider<Movie> dataProviderGlobal = new ListDataProvider<>();

	// Connect the table to the data provider.
	dataProvider.addDataDisplay(movieTable);
	dataProviderGlobal.addDataDisplay(globalMovieTable);
	
	// Add the data to the data provider, which
	// automatically pushes it to the
	// widget.
	Movie_App.list = dataProvider.getList();
	Movie_App.globalList = dataProviderGlobal.getList();

	// Add pager
	SimplePager pager = new SimplePager();
	pager.setDisplay(movieTable);
	// set style name for CSS color
	pager.setStyleName("pager");
	
	// Add table and pager to verticalpanel
	tablePanel = new VerticalPanel();
	tablePanel.add(pager);
	tablePanel.add(movieTable);

	// Add a ColumnSortEvent.ListHandler to connect
	// sorting to the
	// java.util.List.
	ListHandler<Movie> columnSortHandler = new ListHandler<Movie>(Movie_App.list);
	ListHandler<Movie> globalcolumnSortHandler = new ListHandler<Movie>(Movie_App.globalList);
	// name sorting
	columnSortHandler.setComparator(nameColumn, new Comparator<Movie>() {
		public int compare(Movie o1, Movie o2) {
			if (o1 == o2) {
				return 0;
			}

			// Compare the name columns.
			if (o1 != null) {
				return (o2 != null) ? o1.name.compareTo(o2.name) : 1;
			}
			return -1;
		}
	});
	// wiki id sorting
	columnSortHandler.setComparator(wikiidColumn, new Comparator<Movie>() {
		public int compare(Movie o1, Movie o2) {
			if (o1 == o2) {
				return 0;
			}

			// Compare the wikiid columns.
			if (o1 != null) {
				return (o2 != null) ? Integer.valueOf(o1.wikiid).compareTo(Integer.valueOf(o2.wikiid)) : 1;
			}
			return -1;
		}
	});
	// boxoffice sorting
	columnSortHandler.setComparator(boxofficeColumn, new Comparator<Movie>() {
		public int compare(Movie o1, Movie o2) {
			if (o1 == o2) {
				return 0;
			}

			// Compare the name columns.
			if (o1 != null) {
				return (o2 != null) ? Integer.valueOf(o1.boxoffice).compareTo(Integer.valueOf(o2.boxoffice)) : 1;
			}
			return -1;
		}
	});
	// runtime sorting
	columnSortHandler.setComparator(runtimeColumn, new Comparator<Movie>() {
		public int compare(Movie o1, Movie o2) {
			if (o1 == o2) {
				return 0;
			}

			// Compare the name columns.
			if (o1 != null) {
				return (o2 != null) ? Integer.valueOf(o1.runtime).compareTo(Integer.valueOf(o2.runtime)) : 1;
			}
			return -1;
		}
	});
	// releasedate sorting
	columnSortHandler.setComparator(releasedateColumn, new Comparator<Movie>() {
		public int compare(Movie o1, Movie o2) {
			if (o1 == o2) {
				return 0;
			}

			// Compare the name columns.
			if (o1 != null) {
				return (o2 != null) ? Integer.valueOf(o1.releasedate).compareTo(Integer.valueOf(o2.releasedate))
						: 1;
			}
			return -1;
		}
	});
	// global

	globalcolumnSortHandler.setComparator(nameColumn, new Comparator<Movie>() {
		public int compare(Movie o1, Movie o2) {
			if (o1 == o2) {
				return 0;
			}

			// Compare the name columns.
			if (o1 != null) {
				return (o2 != null) ? o1.name.compareTo(o2.name) : 1;
			}
			return -1;
		}
	});
	// wiki id sorting
	globalcolumnSortHandler.setComparator(wikiidColumn, new Comparator<Movie>() {
		public int compare(Movie o1, Movie o2) {
			if (o1 == o2) {
				return 0;
			}

			// Compare the wikiid columns.
			if (o1 != null) {
				return (o2 != null) ? Integer.valueOf(o1.wikiid).compareTo(Integer.valueOf(o2.wikiid)) : 1;
			}
			return -1;
		}
	});
	// boxoffice sorting
	globalcolumnSortHandler.setComparator(boxofficeColumn, new Comparator<Movie>() {
		public int compare(Movie o1, Movie o2) {
			if (o1 == o2) {
				return 0;
			}

			// Compare the name columns.
			if (o1 != null) {
				return (o2 != null) ? Integer.valueOf(o1.boxoffice).compareTo(Integer.valueOf(o2.boxoffice)) : 1;
			}
			return -1;
		}
	});
	// runtime sorting
	globalcolumnSortHandler.setComparator(runtimeColumn, new Comparator<Movie>() {
		public int compare(Movie o1, Movie o2) {
			if (o1 == o2) {
				return 0;
			}

			// Compare the name columns.
			if (o1 != null) {
				return (o2 != null) ? Integer.valueOf(o1.runtime).compareTo(Integer.valueOf(o2.runtime)) : 1;
			}
			return -1;
		}
	});
	// releasedate sorting
	globalcolumnSortHandler.setComparator(releasedateColumn, new Comparator<Movie>() {
		public int compare(Movie o1, Movie o2) {
			if (o1 == o2) {
				return 0;
			}

			// Compare the name columns.
			if (o1 != null) {
				return (o2 != null) ? Integer.valueOf(o1.releasedate).compareTo(Integer.valueOf(o2.releasedate))
						: 1;
			}
			return -1;
		}
	});
	movieTable.addColumnSortHandler(columnSortHandler);
	globalMovieTable.addColumnSortHandler(globalcolumnSortHandler);
	// We know that the data is sorted alphabetically by
	// default.
	movieTable.getColumnSortList().push(nameColumn);
	globalMovieTable.getColumnSortList().push(nameColumn);

	return tablePanel;
}

}
