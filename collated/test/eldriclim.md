# eldriclim
###### \java\seedu\address\logic\commands\ScheduleAddCommandTest.java
``` java
public class ScheduleAddCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testScheduleAddCommand() throws Exception {
        EventName eventName = new EventName("Event name");
        EventTime eventTime = new EventTime(LocalDateTime.now(), Duration.ofMinutes(5));
        EventDuration eventDuration = new EventDuration(Duration.ofMinutes(5));

        Set<Index> uniqueMemberIndexes = new HashSet<>();
        uniqueMemberIndexes.add(new Index(0));

        ModelStubAcceptingEventAdded modelStub = new ModelStubAcceptingEventAdded();

        ScheduleAddCommand command;
        command = getScheduleAddCommand(eventName, eventTime, eventDuration,
                uniqueMemberIndexes, modelStub);

        CommandResult commandResult = command.execute();

        ArrayList<ReadOnlyPerson> expectedMember = new ArrayList<>();
        expectedMember.add(TypicalPersons.ALICE);
        Event event = new Event(new MemberList(expectedMember), eventName, eventTime, eventDuration);

        assertEquals(commandResult.feedbackToUser,
                String.format(ScheduleAddCommand.MESSAGE_SUCCESS, event.toString()));

    }

    @Test
    public void invalidIndexTest() throws Exception {

        thrown.expectMessage(ScheduleAddCommand.ERROR_INVALID_INDEX);

        ModelStubAcceptingEventAdded modelStub = new ModelStubAcceptingEventAdded();

        EventName eventName = new EventName("Event name");
        EventTime eventTime = new EventTime(LocalDateTime.now(), Duration.ofMinutes(5));
        EventDuration eventDuration = new EventDuration(Duration.ofMinutes(5));

        Set<Index> uniqueMemberIndexes = new HashSet<>();
        uniqueMemberIndexes.add(new Index(modelStub.getFilteredPersonList().size()));


        ScheduleAddCommand command;
        command = getScheduleAddCommand(eventName, eventTime, eventDuration,
                uniqueMemberIndexes, modelStub);

        command.execute();
    }

    @Test
    public void testScheduleRemoveEquals() throws Exception {

        EventName eventName = new EventName("Event name");
        EventName eventName1 = new EventName("Event name1");
        EventTime eventTime = new EventTime(LocalDateTime.now(), Duration.ofMinutes(5));
        EventTime eventTime1 = new EventTime(LocalDateTime.now(), Duration.ofMinutes(2));
        EventDuration eventDuration = new EventDuration(Duration.ofMinutes(5));
        EventDuration eventDuration1 = new EventDuration(Duration.ofMinutes(2));
        Set<Index> uniqueMemberIndexes = new HashSet<>(Arrays.asList(new Index(0)));
        Set<Index> uniqueMemberIndexes1 = new HashSet<>(Arrays.asList(new Index(1)));


        ScheduleAddCommand s1 = new ScheduleAddCommand(eventName, eventTime, eventDuration, uniqueMemberIndexes);
        ScheduleAddCommand s2Same = new ScheduleAddCommand(eventName, eventTime, eventDuration, uniqueMemberIndexes);
        ScheduleAddCommand s3DifferentName = new ScheduleAddCommand(eventName1, eventTime, eventDuration,
                uniqueMemberIndexes);

        //eventTime and eventDuration has to be similar due to a check done in Event class
        ScheduleAddCommand s4DifferentTime = new ScheduleAddCommand(eventName, eventTime1, eventDuration1,
                uniqueMemberIndexes);

        ScheduleAddCommand s5DifferentMember = new ScheduleAddCommand(eventName, eventTime, eventDuration,
                uniqueMemberIndexes1);

        assertTrue(s1.equals(s2Same));
        assertFalse(s1.equals(s3DifferentName));
        assertFalse(s1.equals(s4DifferentTime));
        assertFalse(s1.equals(s5DifferentMember));
    }

    /**
     * Generates a new ScheduleAddCommand with the details of the given event.
     */
    private ScheduleAddCommand getScheduleAddCommand(EventName eventName, EventTime eventTime,
                                                     EventDuration eventDuration, Set<Index> uniqueMemberIndexes,
                                                     Model model) {
        ScheduleAddCommand command = new ScheduleAddCommand(eventName, eventTime,
                eventDuration, uniqueMemberIndexes);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void restorePerson(ReadOnlyPerson person) throws DuplicatePersonException, PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void restorePerson(ArrayList<ReadOnlyPerson> person) throws DuplicatePersonException,
                PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void sortPerson(Comparator<ReadOnlyPerson> sortType, boolean isDescending) throws EmptyListException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData, ReadOnlyAddressBook newRecycleibin) {
            fail("This method should not be called.");
        }

        @Override
        public void resetRecyclebin(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ReadOnlyAddressBook getRecycleBin() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        public void deletePerson(ArrayList<ReadOnlyPerson> targets) throws PersonNotFoundException {
            fail("This method should not be called");
        }

        public void deleteBinPerson(ArrayList<ReadOnlyPerson> targets) throws PersonNotFoundException {
            fail("This method should not be called");
        }

        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        public ObservableList<ReadOnlyPerson> getRecycleBinPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ObservableList<Event> getEventList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
        }

        public void updateFilteredBinList(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void updateListOfPerson(ArrayList<ReadOnlyPerson> targets, ArrayList<ReadOnlyPerson> editedPersons)
                throws DuplicatePersonException, PersonNotFoundException {
            fail("This method should not be called.");

        }

        @Override
        public void addEvent(ArrayList<ReadOnlyPerson> targets, ArrayList<ReadOnlyPerson> editedPersons, Event event)
                throws DuplicateEventException, DuplicatePersonException, PersonNotFoundException {
            fail("This method should not be called.");

        }

        @Override
        public void removeEvents(ArrayList<ReadOnlyPerson> targets, ArrayList<ReadOnlyPerson> editedPersons,
                                 ArrayList<Event> toRemoveEvents)
                throws DuplicatePersonException, PersonNotFoundException, EventNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void sortEvents(LocalDate date) {
            fail("This method should not be called.");
        }

        @Override
        public boolean hasEvenClashes(Event event) {
            fail("This method should not be called.");
            return false;
        }

    }

    /**
     * A Model stub that always accept the event being added.
     */
    private class ModelStubAcceptingEventAdded extends ModelStub {
        final ArrayList<Event> eventsAdded = new ArrayList<>();

        @Override
        public void addEvent(ArrayList<ReadOnlyPerson> targets, ArrayList<ReadOnlyPerson> editedPersons, Event event)
                throws DuplicateEventException, DuplicatePersonException, PersonNotFoundException {

            ArrayList<ReadOnlyPerson> members = new ArrayList<>();
            targets.forEach(p -> members.add(p));

            event.setMemberList(new MemberList(targets));
            eventsAdded.add(new Event(event));
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            ArrayList<ReadOnlyPerson> stubList = new ArrayList<>();
            stubList.add(TypicalPersons.ALICE);
            stubList.add(TypicalPersons.BOB);
            stubList.add(TypicalPersons.CARL);

            return FXCollections.observableArrayList(stubList);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }

        @Override
        public ReadOnlyAddressBook getRecycleBin() {
            return new AddressBook();
        }

        @Override
        public boolean hasEvenClashes(Event event) {
            return false;
        }

        //Overwrite default model stub as filtered list is updated during execution
        @Override
        public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {

        }
    }
}
```
###### \java\seedu\address\logic\commands\ScheduleRemoveCommandTest.java
``` java
public class ScheduleRemoveCommandTest {

    @Test
    public void testScheduleRemoveCommand() throws Exception {
        Set<Index> eventListIndex = new HashSet<>();
        eventListIndex.add(new Index(0));

        ModelStubAcceptingEventRemoved modelStub = new ModelStubAcceptingEventRemoved();

        ScheduleRemoveCommand command;

        command = getScheduleRemoveCommand(eventListIndex, modelStub);

        CommandResult commandResult = command.execute();


        ArrayList<ReadOnlyPerson> members = new ArrayList<>();
        members.add(TypicalPersons.ALICE);

        Event expectedEventToRemove = new Event(new MemberList(members), new EventName("Event name"),
                new EventTime(LocalDateTime.now(), Duration.ofMinutes(5)),
                new EventDuration(Duration.ofMinutes(5)));

        assertEquals(commandResult.feedbackToUser,
                String.format(ScheduleRemoveCommand.MESSAGE_SUCCESS, expectedEventToRemove.toString()));
    }

    public ScheduleRemoveCommand getScheduleRemoveCommand(Set<Index> eventListIndex, Model model) {
        ScheduleRemoveCommand command = new ScheduleRemoveCommand(eventListIndex);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    @Test
    public void testScheduleRemoveEquals() {
        ScheduleRemoveCommand s1 = new ScheduleRemoveCommand(new HashSet<>(Arrays.asList(new Index(0))));
        ScheduleRemoveCommand s2 = new ScheduleRemoveCommand(new HashSet<>(Arrays.asList(new Index(0))));
        ScheduleRemoveCommand s3 = new ScheduleRemoveCommand(new HashSet<>(Arrays.asList(new Index(1))));

        assertTrue(s1.equals(s2));
        assertFalse(s1.equals(s3));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void restorePerson(ReadOnlyPerson person) throws DuplicatePersonException, PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void restorePerson(ArrayList<ReadOnlyPerson> person) throws DuplicatePersonException,
                PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void sortPerson(Comparator<ReadOnlyPerson> sortType, boolean isDescending) throws EmptyListException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData, ReadOnlyAddressBook newRecycleibin) {
            fail("This method should not be called.");
        }

        @Override
        public void resetRecyclebin(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ReadOnlyAddressBook getRecycleBin() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        public void deletePerson(ArrayList<ReadOnlyPerson> targets) throws PersonNotFoundException {
            fail("This method should not be called");
        }

        public void deleteBinPerson(ArrayList<ReadOnlyPerson> targets) throws PersonNotFoundException {
            fail("This method should not be called");
        }

        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        public ObservableList<ReadOnlyPerson> getRecycleBinPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ObservableList<Event> getEventList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
        }

        public void updateFilteredBinList(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void updateListOfPerson(ArrayList<ReadOnlyPerson> targets, ArrayList<ReadOnlyPerson> editedPersons)
                throws DuplicatePersonException, PersonNotFoundException {
            fail("This method should not be called.");

        }

        @Override
        public void addEvent(ArrayList<ReadOnlyPerson> targets, ArrayList<ReadOnlyPerson> editedPersons, Event event)
                throws DuplicateEventException, DuplicatePersonException, PersonNotFoundException {
            fail("This method should not be called.");

        }

        @Override
        public void removeEvents(ArrayList<ReadOnlyPerson> targets, ArrayList<ReadOnlyPerson> editedPersons,
                                 ArrayList<Event> toRemoveEvents)
                throws DuplicatePersonException, PersonNotFoundException, EventNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void sortEvents(LocalDate date) {
            fail("This method should not be called.");
        }

        @Override
        public boolean hasEvenClashes(Event event) {
            fail("This method should not be called.");
            return false;
        }

    }

    /**
     * A Model stub that always accept the event being removed.
     */
    private class ModelStubAcceptingEventRemoved extends ModelStub {

        private ArrayList<Event> eventList = new ArrayList<>();
        private AddressBook addressBookStub = new AddressBook();
        private ObservableList<ReadOnlyPerson> filteredPersonListStub;

        @Override
        public void removeEvents(ArrayList<ReadOnlyPerson> targets, ArrayList<ReadOnlyPerson> editedPersons,
                                 ArrayList<Event> toRemoveEvents)
                throws DuplicatePersonException, PersonNotFoundException, EventNotFoundException {

            toRemoveEvents.forEach(e -> eventList.remove(e));
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            ArrayList<ReadOnlyPerson> stubList = new ArrayList<>();
            stubList.add(TypicalPersons.ALICE);
            stubList.add(TypicalPersons.BOB);
            stubList.add(TypicalPersons.CARL);

            return FXCollections.observableArrayList(stubList);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return addressBookStub;
        }

        @Override
        public ReadOnlyAddressBook getRecycleBin() {
            return new AddressBook();
        }

        /**
         * Returns an ObservableList of Events with a single event containing one members.
         *
         * @return
         */
        @Override
        public ObservableList<Event> getEventList() {
            try {
                eventList.clear();
                Event e1 = new Event(new MemberList(), new EventName("Event name"),
                        new EventTime(LocalDateTime.now(), Duration.ofMinutes(5)),
                        new EventDuration(Duration.ofMinutes(5)));

                eventList.add(e1);

                Person alice = new PersonBuilder().withName("Alice Pauline")
                        .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
                        .withPhone("85355255")
                        .withTags("friends").build();
                alice.setEvents(new HashSet<>(Arrays.asList(eventList.get(0))));

                ReadOnlyPerson aliceReadOnly = new Person(alice);
                addressBookStub.addPerson(aliceReadOnly);
                addressBookStub.addPerson(TypicalPersons.BOB);
                addressBookStub.addPerson(TypicalPersons.CARL);
                ArrayList<ReadOnlyPerson> members = new ArrayList<>();
                members.add(aliceReadOnly);

                e1.setMemberList(new MemberList(members));

            } catch (IllegalValueException e) {
                //Should not reach this point, try-catch is used to bypass Override restriction
                e.printStackTrace();
            }

            return FXCollections.observableArrayList(eventList);

        }

        @Override
        public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
            filteredPersonListStub = new FilteredList<ReadOnlyPerson>(addressBookStub.getPersonList());
            filteredPersonListStub.filtered(predicate);
        }
    }
}
```
###### \java\seedu\address\logic\commands\SortCommandTest.java
``` java
public class SortCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Person validPerson1 = new PersonBuilder()
            .withName("C").withPhone("465").withEmail("z@z").withAddress("a").build();
    private Person validPerson2 = new PersonBuilder()
            .withName("B").withPhone("123").withEmail("d@d").withAddress("s").build();
    private Person validPerson3 = new PersonBuilder()
            .withName("A").withPhone("987").withEmail("f@f").withAddress("b").build();

    @Test
    public void constructor_nullSortType_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new SortCommand(null, true);
    }

    @Test
    public void constructor_nullSortOrder_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new SortCommand(CliSyntax.PREFIX_NAME.toString(), null);
    }

    @Test
    public void execute_catchEmptyListException() throws CommandException {
        thrown.expect(CommandException.class);

        ModelStubAcceptingPersonForSort modelStub = new ModelStubAcceptingPersonForSort();
        getSortCommandForPerson("n/", false, modelStub).execute();
    }

    @Test
    public void test_getSortCommandResult_sortSuccessful() throws Exception {
        ModelStubAcceptingPersonForSort modelStub = new ModelStubAcceptingPersonForSort();
        populateModel(modelStub);

        String sortType = "/n";
        Boolean isDescending = false;
        CommandResult commandResult = getSortCommandForPerson(sortType, isDescending, modelStub).execute();

        assertEquals(String.format(SortCommand.MESSAGE_SORT_LIST_SUCCESS, "name", "ascending"),
                commandResult.feedbackToUser);
    }

    @Test
    public void testComparator() throws Exception {
        ModelStubAcceptingPersonForSort modelStub = new ModelStubAcceptingPersonForSort();
        populateModel(modelStub);

        ArrayList<Person> expectedList = new ArrayList<>();
        expectedList.add(validPerson1);
        expectedList.add(validPerson2);
        expectedList.add(validPerson3);

        //Test comparator - sort by name
        expectedList.sort((o1, o2) -> o1.getName().toString().compareToIgnoreCase(o2.getName().toString()));
        getSortCommandForPerson(CliSyntax.PREFIX_NAME.toString(), false, modelStub).execute();
        assertEquals(expectedList, modelStub.personsAdded);

        //Test comparator - sort by phone
        expectedList.sort((o1, o2) -> o1.getPhone().toString().compareToIgnoreCase(o2.getPhone().toString()));
        getSortCommandForPerson(CliSyntax.PREFIX_PHONE.toString(), false, modelStub).execute();
        assertEquals(expectedList, modelStub.personsAdded);

        //Test comparator - sort by email
        expectedList.sort((o1, o2) -> o1.getEmail().toString().compareToIgnoreCase(o2.getEmail().toString()));
        getSortCommandForPerson(CliSyntax.PREFIX_EMAIL.toString(), false, modelStub).execute();
        assertEquals(expectedList, modelStub.personsAdded);

        //Test comparator - sort by address
        expectedList.sort((o1, o2) -> o1.getAddress().toString().compareToIgnoreCase(o2.getAddress().toString()));
        getSortCommandForPerson(CliSyntax.PREFIX_ADDRESS.toString(), false, modelStub).execute();
        assertEquals(expectedList, modelStub.personsAdded);

        //Test comparator - sort by date added
        expectedList.sort(Comparator.comparing(o -> o.getDateAdded().getDateObject()));
        getSortCommandForPerson(CliSyntax.PREFIX_DATEADDED.toString(), false, modelStub).execute();
        assertEquals(expectedList, modelStub.personsAdded);

    }


    /**
     * Generates a new AddCommand with the details of the given person.
     */
    private AddCommand getAddCommandForPerson(Person person, Model model) {
        AddCommand command = new AddCommand(person);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Generates a new SortCommand with the details of the given list.
     */
    private SortCommand getSortCommandForPerson(String sortType, boolean isDescending, Model model) {
        SortCommand command = new SortCommand(sortType, isDescending);
        command.setData(model, new CommandHistory(), new UndoRedoStack());

        return command;
    }

    /**
     * Populate model list with persons
     */
    private void populateModel(Model modelStub) throws Exception {
        getAddCommandForPerson(validPerson1, modelStub).execute();
        getAddCommandForPerson(validPerson2, modelStub).execute();
        getAddCommandForPerson(validPerson3, modelStub).execute();
    }


    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void restorePerson(ReadOnlyPerson person) throws DuplicatePersonException, PersonNotFoundException {
            fail("This method should not be called.");
        }
        @Override
        public void restorePerson(ArrayList<ReadOnlyPerson> person) throws DuplicatePersonException,
                PersonNotFoundException {
            fail("This method should not be called.");
        }


        @Override
        public void sortPerson(Comparator<ReadOnlyPerson> sortType, boolean isDescending) throws EmptyListException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData, ReadOnlyAddressBook newRecyclebin) {
            fail("This method should not be called.");
        }

        @Override
        public void resetRecyclebin(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }


        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        public ReadOnlyAddressBook getRecycleBin() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void deletePerson(ArrayList<ReadOnlyPerson> targets) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        public void deleteBinPerson(ArrayList<ReadOnlyPerson> targets) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        public ObservableList<ReadOnlyPerson> getRecycleBinPersonList() {
            fail("This method should not be called.");
            return null;
        }


        @Override
        public ObservableList<Event> getEventList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
        }

        public void updateFilteredBinList(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
        }


        @Override
        public void updateListOfPerson(ArrayList<ReadOnlyPerson> targets, ArrayList<ReadOnlyPerson> editedPersons)
                throws DuplicatePersonException, PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void addEvent(ArrayList<ReadOnlyPerson> targets, ArrayList<ReadOnlyPerson> editedPersons, Event event)
                throws DuplicateEventException, DuplicatePersonException, PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void removeEvents(ArrayList<ReadOnlyPerson> targets, ArrayList<ReadOnlyPerson> editedPersons,
                                 ArrayList<Event> toRemoveEvents)
                throws DuplicatePersonException, PersonNotFoundException, EventNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void sortEvents(LocalDate date) {
            fail("This method should not be called.");
        }

        @Override
        public boolean hasEvenClashes(Event event) {
            fail("This method should not be called.");
            return false;
        }
    }

    /**
     * A Model stub that always accept the person being added and sort list.
     */
    private class ModelStubAcceptingPersonForSort extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            personsAdded.add(new Person(person));
        }

        @Override
        public void sortPerson(Comparator<ReadOnlyPerson> sortType, boolean isDescending) throws EmptyListException {
            if (personsAdded.size() < 1) {
                throw new EmptyListException();
            }

            personsAdded.sort(sortType);
            if (isDescending) {
                Collections.reverse(personsAdded);
            }
        }

        @Override
        public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {

        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }

        @Override
        public ReadOnlyAddressBook getRecycleBin() {
            return new AddressBook();
        }
    }


}
```
###### \java\seedu\address\logic\parser\ScheduleAddCommandParserTest.java
``` java
public class ScheduleAddCommandParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    final String fullArgs = " m/1 n/Event name t/2017-01-01 10:00 d/5m";
    final String noMembersArgs = "  n/Event name t/2017-01-01 10:00 d/5m";
    final String noDurationArgs = " m/1 n/Event name t/2017-01-01 10:00";
    final String noMemberAndDurationArgs = " n/Event name t/2017-01-01 10:00";

    final String invalidDateFormat = " m/1 n/Event name t/2017-02-29 10:00 d/5m";
    final String invalidDurationFormat = " n/Event name t/2017-01-01 10:00 d/5m2h";
    final String invalidNoNameArgs = " t/2017-01-01 10:00";
    final String invalidNoTimeArgs = " n/Event name";

    private ScheduleAddCommandParser parser = new ScheduleAddCommandParser();

    @Test
    public void parseSuccessTest() throws IllegalValueException {
        EventName eventName = new EventName("Event name");
        Duration duration = Duration.ofMinutes(5);
        EventTime eventTime = new EventTime(LocalDateTime.of(2017, 1, 1, 10, 0),
                duration);
        EventDuration eventDuration = new EventDuration(duration);
        Set<Index> uniqueMemberIndexes = new HashSet<>();
        uniqueMemberIndexes.add(new Index(0));


        assertParseSuccess(parser, ScheduleAddCommand.COMMAND_WORD + fullArgs,
                new ScheduleAddCommand(eventName, eventTime, eventDuration, uniqueMemberIndexes));

        assertParseSuccess(parser, ScheduleAddCommand.COMMAND_WORD + noMembersArgs,
                new ScheduleAddCommand(eventName, eventTime, eventDuration, new HashSet<>()));


        //Set duration to default 0 mins
        eventTime = new EventTime(LocalDateTime.of(2017, 1, 1, 10, 0),
                Duration.ofMinutes(0));
        eventDuration = new EventDuration(Duration.ofMinutes(0));

        assertParseSuccess(parser, ScheduleAddCommand.COMMAND_WORD + noDurationArgs,
                new ScheduleAddCommand(eventName, eventTime, eventDuration, uniqueMemberIndexes));

        assertParseSuccess(parser, ScheduleAddCommand.COMMAND_WORD + noMemberAndDurationArgs,
                new ScheduleAddCommand(eventName, eventTime, eventDuration, new HashSet<>()));


    }

    @Test
    public void invalidDateTest() throws ParseException {
        thrown.expectMessage(ScheduleAddCommandParser.ERROR_INVALID_DATE);
        parser.parse(invalidDateFormat);
    }

    @Test
    public void invalidDurationFormatTest() throws ParseException {
        thrown.expectMessage(ScheduleAddCommandParser.ERROR_PARSING_DURATION);
        parser.parse(invalidDurationFormat);
    }

    @Test
    public void parseFailureTest() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleAddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, ScheduleAddCommand.COMMAND_WORD + invalidNoNameArgs,
                expectedMessage);

        // missing time prefix
        assertParseFailure(parser, ScheduleAddCommand.COMMAND_WORD + invalidNoTimeArgs,
                expectedMessage);

        // missing prefixes
        assertParseFailure(parser, ScheduleAddCommand.COMMAND_WORD,
                expectedMessage);
    }
}
```
###### \java\seedu\address\logic\parser\ScheduleRemoveCommandParserTest.java
``` java
public class ScheduleRemoveCommandParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ScheduleRemoveCommandParser parser = new ScheduleRemoveCommandParser();

    private String fullArgs = " I/1";
    private String invalidIndex = " I/a";

    @Test
    public void parseSuccessTest() throws Exception {
        assertParseSuccess(parser, ScheduleRemoveCommand.COMMAND_WORD + fullArgs,
                new ScheduleRemoveCommand(new HashSet<>(Arrays.asList(new Index(0)))));

    }

    @Test
    public void parseFailureTest() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleRemoveCommand.MESSAGE_USAGE);

        assertParseFailure(parser, ScheduleRemoveCommand.COMMAND_WORD + invalidIndex, MESSAGE_INVALID_INDEX);

        assertParseFailure(parser, ScheduleRemoveCommand.COMMAND_WORD, expectedMessage);
    }

}
```
###### \java\seedu\address\logic\parser\SortCommandParserTest.java
``` java
public class SortCommandParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private SortCommandParser parser = new SortCommandParser();


    @Test
    public void parseFailure() {
        //More than 1 argument inputted
        assertParseFailure(parser, WHITESPACE + CliSyntax.PREFIX_NAME
                        + SortCommand.BY_ASCENDING + WHITESPACE + CliSyntax.PREFIX_PHONE + SortCommand.BY_ASCENDING,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        //Gibberish arguments inputted, with one valid argument
        assertParseFailure(parser, WHITESPACE + "gibberish/g" + CliSyntax.PREFIX_PHONE + SortCommand.BY_ASCENDING,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        //Sort order not recognised
        assertParseFailure(parser, WHITESPACE + CliSyntax.PREFIX_PHONE + "gibberish",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }


    @Test
    public void parseSuccess() {

        assertParseSuccess(parser, "", new SortCommand("n/", false));

        assertParseSuccess(parser, WHITESPACE + CliSyntax.PREFIX_NAME
                + SortCommand.BY_ASCENDING, new SortCommand("n/", false));

        assertParseSuccess(parser, WHITESPACE + CliSyntax.PREFIX_PHONE
                + SortCommand.BY_ASCENDING, new SortCommand("p/", false));

        assertParseSuccess(parser, WHITESPACE + CliSyntax.PREFIX_EMAIL
                + SortCommand.BY_ASCENDING, new SortCommand("e/", false));

        assertParseSuccess(parser, WHITESPACE + CliSyntax.PREFIX_ADDRESS
                + SortCommand.BY_ASCENDING, new SortCommand("a/", false));

        assertParseSuccess(parser, WHITESPACE + CliSyntax.PREFIX_DATEADDED
                + SortCommand.BY_ASCENDING, new SortCommand("t/", false));

        assertParseSuccess(parser, WHITESPACE + CliSyntax.PREFIX_NAME
                + SortCommand.BY_DESCENDING, new SortCommand("n/", true));

        assertParseSuccess(parser, WHITESPACE + CliSyntax.PREFIX_PHONE
                + SortCommand.BY_DESCENDING, new SortCommand("p/", true));

        assertParseSuccess(parser, WHITESPACE + CliSyntax.PREFIX_EMAIL
                + SortCommand.BY_DESCENDING, new SortCommand("e/", true));

        assertParseSuccess(parser, WHITESPACE + CliSyntax.PREFIX_ADDRESS
                + SortCommand.BY_DESCENDING, new SortCommand("a/", true));

        assertParseSuccess(parser, WHITESPACE + CliSyntax.PREFIX_DATEADDED
                + SortCommand.BY_DESCENDING, new SortCommand("t/", true));

    }
}
```
###### \java\seedu\address\model\event\EventDurationTest.java
``` java
public class EventDurationTest {

    private EventDuration eventDuration1 = new EventDuration(Duration.ofMinutes(90));
    private EventDuration eventDuration2 = new EventDuration(Duration.ofMinutes(90));
    private EventDuration eventDuration3 = new EventDuration(Duration.ofMinutes(120));
    private EventDuration eventDuration4 = new EventDuration(Duration.ofMinutes(30));
    private EventDuration eventDuration5 = new EventDuration(Duration.ofSeconds(30));

    @Test
    public void testEventDurationEquals() {
        //90min = 90min
        assertTrue(eventDuration1.equals(eventDuration2));

        //90min != 120min
        assertFalse(eventDuration1.equals(eventDuration3));

        //Checks if getEventDuration returns correct Duration value
        assertEquals(eventDuration1.getDuration(), Duration.ofMinutes(90));
    }

    @Test
    public void testEventDurationToString() {
        //90min = 1hr30min
        String output = eventDuration1.toString();
        assertEquals("1hr30min", output);

        //120min = 2hr
        output = eventDuration3.toString();
        assertEquals("2hr", output);

        //30min = 30min
        output = eventDuration4.toString();
        assertEquals("30min", output);

        //30sec = 0min
        output = eventDuration5.toString();
        assertEquals("0min", output);

    }

    @Test
    public void testEventDurationHashcode() {
        assertEquals(eventDuration1.hashCode(), Duration.ofMinutes(90).hashCode());
    }
}
```
###### \java\seedu\address\model\event\EventNameTest.java
``` java
public class EventNameTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void testInvalidInput() throws IllegalValueException {
        thrown.expect(IllegalValueException.class);
        EventName e1 = new EventName("");
    }

    @Test
    public void testEventNameCreationSuccess() throws IllegalValueException {
        EventName e = new EventName("Event 1");
        EventName e1 = new EventName("Event 1");
        EventName e2 = new EventName("Event 3");

        assertEquals("Event 1", e.toString());
        assertEquals("Event 1".hashCode(), e.hashCode());
        assertTrue(e.equals(e1));
        assertTrue(EventName.isValidName(e1.toString()));
        assertFalse(e.equals(e2));

    }

}
```
###### \java\seedu\address\model\event\EventTest.java
``` java
public class EventTest {

    private Event e1;
    private Event e2;
    private Event e3;
    private Event e4;

    private EventName eventNameTest;
    private EventTime eventTimeTest;
    private EventDuration eventDurationTest;
    private MemberList eventMemberListTest;

    @Test
    public void testEventCreation() throws IllegalValueException {
        ArrayList<ReadOnlyPerson> list = new ArrayList<>();
        list.add(TypicalPersons.ALICE);
        list.add(TypicalPersons.CARL);

        e1 = new Event(new MemberList(list), new EventName("Event name"),
                new EventTime(LocalDateTime.of(2017, 2, 7, 8, 0, 30), Duration.ofMinutes(90)),
                new EventDuration(Duration.ofMinutes(90)));
        e2 = new Event(new MemberList(list), new EventName("Event name"),
                new EventTime(LocalDateTime.of(2017, 2, 7, 8, 0, 30), Duration.ofMinutes(90)),
                new EventDuration(Duration.ofMinutes(90)));

        //Different time from e1
        e3 = new Event(new MemberList(list), new EventName("Event name"),
                new EventTime(LocalDateTime.now(), Duration.ofMinutes(90)),
                new EventDuration(Duration.ofMinutes(90)));

        //Different name from e1
        e4 = new Event(new MemberList(list), new EventName("Event name different"),
                new EventTime(LocalDateTime.of(2017, 2, 7, 8, 0, 30), Duration.ofMinutes(90)),
                new EventDuration(Duration.ofMinutes(90)));

        eventNameTest = new EventName("Event name");
        eventTimeTest = new EventTime(LocalDateTime.of(2017, 2, 7, 8, 0, 30),
                Duration.ofMinutes(90));
        eventDurationTest = new EventDuration(Duration.ofMinutes(90));
        eventMemberListTest = new MemberList(list);

        testEventEqual();
        testOutput();
        testGetter();
        testSetter();
        testUpdateStatus();
    }

    private void testUpdateStatus() {
        e1.updateEventStatusSelection(e1.getEventTime().getStart().toLocalDate());
        assertEquals(e1.getEventStatusStyle(), "-fx-background-color: #b91372");
    }

    @Test
    public void overloadedConstructorTest() throws IllegalValueException {
        Event e1 = new Event(new MemberList(), new EventName("Event name"),
                new EventTime(LocalDateTime.now(), Duration.ofMinutes(5)),
                new EventDuration(Duration.ofMinutes(5)));
        Event e2 = new Event(e1);

        assertEquals(e1, e2);
    }

    /**
     * Test equals function of Event
     */
    public void testEventEqual() {
        assertTrue(e1.equals(e2));
        assertFalse(e1.equals(e3));
        assertFalse(e1.equals(e4));
    }

    /**
     * Test hashcode and toString of Event
     *
     * @throws IllegalValueException
     */
    public void testOutput() throws IllegalValueException {

        //Test hashcode
        assertEquals(Objects.hash(eventNameTest, eventTimeTest, eventDurationTest),
                e1.hashCode());

        //Test toString
        assertEquals("Name: Event name Time: 2017-02-07 08:00 Duration: 1hr30min\n"
                + "Members: Alice Pauline, Carl Kurz", e1.toString());
    }

    /**
     * Test Property getter of Event
     */
    public void testGetter() {
        assertEquals("ObjectProperty [value: Alice Pauline, Carl Kurz]",
                e1.eventMemberListProperty().toString());
        assertEquals("ObjectProperty [value: Event name]",
                e1.eventNameProperty().toString());
        assertEquals("ObjectProperty [value: 2017-02-07 08:00]",
                e1.eventTimeProperty().toString());
        assertEquals("ObjectProperty [value: 1hr30min]",
                e1.eventDurationProperty().toString());
        assertEquals("ObjectProperty [value: Past]",
                e1.eventStatusProperty().toString());
        assertEquals("ObjectProperty [value: -fx-background-color: #a31621]",
                e1.eventStatusStyleProperty().toString());
    }

    /**
     * Test Setter of Event
     */
    private void testSetter() throws IllegalValueException {

        ArrayList<ReadOnlyPerson> list = new ArrayList<>();
        list.add(TypicalPersons.GEORGE);
        list.add(TypicalPersons.HOON);
        LocalDateTime futureDate = LocalDateTime.now().plusDays(1);
        Event actual = e4;
        Event expected = new Event(new MemberList(list), new EventName("For testing setter"),
                new EventTime(futureDate, Duration.ofMinutes(60)),
                new EventDuration(Duration.ofMinutes(60)));

        //Before setting
        assertFalse(actual.equals(expected));

        actual.setEventName(new EventName("For testing setter"));
        actual.setEventTime(new EventTime(futureDate, Duration.ofMinutes(60)));
        actual.setEventDuration(new EventDuration(Duration.ofMinutes(60)));
        actual.setMemberList(new MemberList(list));


        //After setting
        assertTrue(actual.equals(expected));

    }
}
```
###### \java\seedu\address\model\event\EventTimeTest.java
``` java
public class EventTimeTest {

    @Test
    public void testEventTimeCreationSuccess() {
        LocalDateTime now = LocalDateTime.now();
        Duration d = Duration.ofMinutes(90);

        EventTime time1 = new EventTime(now, d);
        EventTime time2 = new EventTime(now, d);
        EventTime time3 = new EventTime(now, Duration.ofMinutes(10));
        EventTime time4 = new EventTime(now.plus(d), d);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        assertEquals(now.format(formatter), time1.toString());
        assertEquals(now, time1.getStart());
        assertEquals(now.plus(d), time1.getEnd());
        assertEquals(Objects.hash(now, d), time1.hashCode());

        assertTrue(time1.equals(time2));
        assertFalse(time1.equals(time3));
        assertFalse(time1.equals(time4));
    }

    @Test
    public void testEventTimeUpcoming() {

        EventTime futureEvent = new EventTime(LocalDateTime.now().plus(Duration.ofDays(3)),
                Duration.ofMinutes(10));
        EventTime pastEvent = new EventTime(LocalDateTime.now().minus(Duration.ofDays(3)),
                Duration.ofMinutes(10));

        assertTrue(futureEvent.isUpcoming());
        assertFalse(pastEvent.isUpcoming());


    }
}
```
###### \java\seedu\address\model\event\MemberListTest.java
``` java
public class MemberListTest {

    @Test
    public void testMemberListCreationNonEmpty() {

        ArrayList<ReadOnlyPerson> list1 = new ArrayList<>();
        list1.add(TypicalPersons.ALICE);
        list1.add(TypicalPersons.BOB);
        MemberList m1 = new MemberList(list1);

        ArrayList<ReadOnlyPerson> list2 = new ArrayList<>();
        list2.add(TypicalPersons.ALICE);
        list2.add(TypicalPersons.BOB);
        MemberList m2 = new MemberList(list2);

        ArrayList<ReadOnlyPerson> list3 = new ArrayList<>();
        list3.add(TypicalPersons.DANIEL);
        list3.add(TypicalPersons.CARL);
        MemberList m3 = new MemberList(list3);

        MemberList m4Empty = new MemberList();

        //Test contains
        assertTrue(m1.contains(TypicalPersons.ALICE));
        assertFalse(m1.contains(TypicalPersons.AMY));

        //Test isEmpty
        assertFalse(m1.isEmpty());

        //Test toString
        assertEquals(EventOutputUtil.toStringMembers(list1), m1.toString());

        //Test equals
        assertTrue(m1.equals(m2));
        assertFalse(m1.equals(m3));
        assertFalse(m1.equals(m4Empty));

        //Test readOnlyList
        assertEquals(list1, m1.asReadOnlyMemberList());

    }

    @Test
    public void testMemberListCreationEmpty() {

        MemberList m1 = new MemberList();
        MemberList m2 = new MemberList();

        //Non-empty list for testing equals function
        ArrayList<ReadOnlyPerson> list = new ArrayList<>();
        list.add(TypicalPersons.ALICE);
        MemberList m3 = new MemberList(list);

        assertTrue(m1.isEmpty());
        assertTrue(m1.equals(m2));

        assertFalse(m1.contains(TypicalPersons.ALICE));

        assertEquals("none", m1.toString());

        //Empty list for comparing hashcode
        ArrayList<ReadOnlyPerson> emptyList = new ArrayList<>();
        assertEquals(emptyList.hashCode(), m1.hashCode());

        //Test equals
        assertFalse(m1.equals(m3));
        assertTrue(m1.equals(m2));

    }

    @Test
    public void testReadOnlyList() {

        //Test read-only list
        ArrayList<ReadOnlyPerson> listEmpty = new ArrayList<>();
        MemberList m1Empty = new MemberList();

        ArrayList<ReadOnlyPerson> list = new ArrayList<>();
        list.add(TypicalPersons.ALICE);
        MemberList m2 = new MemberList(list);

        assertEquals(Collections.unmodifiableList(listEmpty), m1Empty.asReadOnlyMemberList());
        assertEquals(Collections.unmodifiableList(list), m2.asReadOnlyMemberList());
    }

}
```
###### \java\seedu\address\model\UniqueEventListTest.java
``` java
public class UniqueEventListTest {
    private UniqueEventList eventList = new UniqueEventList();

    @Test
    public void createUniqueEventListSuccess() throws IllegalValueException {
        ArrayList<ReadOnlyPerson> list = new ArrayList<>();
        list.add(TypicalPersons.ALICE);
        list.add(TypicalPersons.BOB);
        LocalDateTime now = LocalDateTime.now();

        Event e1 = new Event(new MemberList(list), new EventName("1"),
                new EventTime(now, Duration.ofMinutes(10)),
                new EventDuration(Duration.ofMinutes(10)));
        Event e2 = new Event(new MemberList(list), new EventName("1"),
                new EventTime(now, Duration.ofMinutes(10)),
                new EventDuration(Duration.ofMinutes(10)));
        Event e3 = new Event(new MemberList(list), new EventName("2"),
                new EventTime(now, Duration.ofMinutes(10)),
                new EventDuration(Duration.ofMinutes(10)));
        Event e4 = new Event(new MemberList(list), new EventName("1"),
                new EventTime(now.plus(Duration.ofMinutes(1)), Duration.ofMinutes(10)),
                new EventDuration(Duration.ofMinutes(10)));
        Event e5 = new Event(new MemberList(list), new EventName("1"),
                new EventTime(now, Duration.ofMinutes(1)),
                new EventDuration(Duration.ofMinutes(1)));

        eventList.add(e1);
        eventList.add(e3);
        eventList.add(e4);
        eventList.add(e5);

        //Test contains
        assertTrue(eventList.contains(e1));
        assertTrue(eventList.contains(new Event(new MemberList(list), new EventName("1"),
                new EventTime(now, Duration.ofMinutes(10)),
                new EventDuration(Duration.ofMinutes(10)))));

        //Test equals
        assertTrue(e1.equals(e2));
        assertTrue(e1.equals(new Event(new MemberList(list), new EventName("1"),
                new EventTime(now, Duration.ofMinutes(10)),
                new EventDuration(Duration.ofMinutes(10)))));
        assertFalse(e1.equals(e3));
    }

    @Test
    public void testEventClashes() throws IllegalValueException {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime past = now.minus(Duration.ofHours(1));
        LocalDateTime future = now.plus(Duration.ofHours(1));

        Event e1 = new Event(new MemberList(), new EventName("1"),
                new EventTime(now, Duration.ofHours(2)),
                new EventDuration(Duration.ofHours(2)));
        eventList.add(e1);

        //Test for overlaps
        assertTrue(eventList.hasClashes(new Event(new MemberList(), new EventName("1"),
                new EventTime(future, Duration.ofHours(2)),
                new EventDuration(Duration.ofHours(2)))));

        //Test for in between
        assertTrue(eventList.hasClashes(new Event(new MemberList(), new EventName("1"),
                new EventTime(future, Duration.ofMinutes(30)),
                new EventDuration(Duration.ofMinutes(30)))));

        //Test for exact date-time match
        assertTrue(eventList.hasClashes(new Event(new MemberList(), new EventName("1"),
                new EventTime(now, Duration.ofHours(2)),
                new EventDuration(Duration.ofHours(2)))));

        //Test for adjacent events
        assertFalse(eventList.hasClashes(new Event(new MemberList(), new EventName("1"),
                new EventTime(now.minus(Duration.ofHours(2)), Duration.ofHours(1)),
                new EventDuration(Duration.ofHours(1)))));
        assertFalse(eventList.hasClashes(new Event(new MemberList(), new EventName("1"),
                new EventTime(now.plus(Duration.ofHours(2)), Duration.ofHours(1)),
                new EventDuration(Duration.ofHours(1)))));

        //Test for adjacent events with time buffer
        assertFalse(eventList.hasClashes(new Event(new MemberList(), new EventName("1"),
                new EventTime(now.minus(Duration.ofHours(5)), Duration.ofHours(1)),
                new EventDuration(Duration.ofHours(1)))));
        assertFalse(eventList.hasClashes(new Event(new MemberList(), new EventName("1"),
                new EventTime(now.plus(Duration.ofHours(5)), Duration.ofHours(1)),
                new EventDuration(Duration.ofHours(1)))));
    }
}
```
