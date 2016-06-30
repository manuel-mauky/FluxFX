package notesapp;

import eu.lestard.fluxfx.Dispatcher;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class NotesStoreTest {

	private NotesStore store;

	@Before
	public void setup() {
		store = new NotesStore();
	}

	@Test
	public void testAddASingleNote() {

		// given
		assertThat(store.getNotes()).isEmpty();

		// when
		Dispatcher.getInstance().dispatch(new AddNoteAction("something"));

		// then
		assertThat(store.getNotes()).contains("something");
	}

	@Test
	public void testAddMultipleNotes() {

		// given
		assertThat(store.getNotes()).isEmpty();

		// when
		Dispatcher.getInstance().dispatch(new AddNoteAction("something"));
		Dispatcher.getInstance().dispatch(new AddNoteAction("second"));
		Dispatcher.getInstance().dispatch(new AddNoteAction("third"));

		// then
		assertThat(store.getNotes()).containsExactly("something", "second", "third");
	}

	@Test
	public void testAddEmptyNotes() {
		// given
		assertThat(store.getNotes()).isEmpty();


		// when
		Dispatcher.getInstance().dispatch(new AddNoteAction(""));
		// then
		assertThat(store.getNotes()).isEmpty();


		// when
		Dispatcher.getInstance().dispatch(new AddNoteAction(null));
		// then
		assertThat(store.getNotes()).isEmpty();


		// when
		Dispatcher.getInstance().dispatch(new AddNoteAction("    "));
		// then
		assertThat(store.getNotes()).isEmpty();
	}


}
