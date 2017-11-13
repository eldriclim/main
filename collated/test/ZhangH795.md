# ZhangH795
###### \java\guitests\ThemeGuiTest.java
``` java
public class ThemeGuiTest extends AddressBookGuiTest {
    /**
     * Asserts that the theme after change is Dark Theme.
     */
    @Test
    public void changeToDarkThemeTest() {
        ArrayList<String> darkTheme = new ArrayList<>();
        darkTheme.add("view/DarkTheme.css");
        ChangeDarkThemeEvent darkThemeEvent = new ChangeDarkThemeEvent();

        postNow(darkThemeEvent);
        assertEquals(darkTheme, stage.getScene().getStylesheets());
    }

    /**
     * Asserts that the theme after change is Bright Theme.
     */
    @Test
    public void changeToBrightThemeTest() {
        ArrayList<String> brightTheme = new ArrayList<>();
        brightTheme.add("view/BrightTheme.css");
        ChangeBrightThemeEvent brightThemeEvent = new ChangeBrightThemeEvent();

        postNow(brightThemeEvent);
        assertEquals(brightTheme, stage.getScene().getStylesheets());
    }

    /**
     * Asserts that the theme after change is Default Theme.
     */
    @Test
    public void changeToDefaultThemeTest() {
        ArrayList<String> defaultTheme = new ArrayList<>();
        defaultTheme.add("view/Extensions.css");
        ChangeDefaultThemeEvent defaultThemeEvent = new ChangeDefaultThemeEvent();

        postNow(defaultThemeEvent);
        assertEquals(defaultTheme, stage.getScene().getStylesheets());
    }
}
```
###### \java\seedu\address\logic\commands\TagAddCommandTest.java
``` java

/**
 * Contains integration tests (interaction with the Model) and unit tests for TagAddCommand.
 */
public class TagAddCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new AddressBook(), new UserPrefs());

    @Test
    public void executeTagAddSinglePerson() throws Exception {
        showFirstPersonOnly(model);

        Set<Tag> singleTagSet = new HashSet<>();
        singleTagSet.add(new Tag(VALID_TAG_HUSBAND));

        ArrayList<Index> singlePersonIndexList = new ArrayList<>();
        singlePersonIndexList.add(INDEX_FIRST_PERSON);
        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        TagAddDescriptor tagAddDescriptor = new TagAddDescriptor(personInFilteredList);
        tagAddDescriptor.setTags(singleTagSet);

        Person editedPerson = new PersonBuilder(personInFilteredList).withATags(VALID_TAG_HUSBAND).build();
        TagAddCommand tagAddCommand = prepareCommand(singlePersonIndexList, tagAddDescriptor);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new AddressBook(),
                new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(tagAddCommand, model, createTagListDisplay(editedPerson), expectedModel);

        tagAddCommand = prepareCommand(singlePersonIndexList,
                new TagAddDescriptor(new PersonBuilder().withATags(VALID_TAG_HUSBAND).build()));
        assertCommandFailure(tagAddCommand, model, String.format(TagAddCommand.MESSAGE_TAG_ALREADY_EXISTS,
                "[" + VALID_TAG_HUSBAND + "]"));
    }

    @Test
    public void executeInvalidPersonIndexUnfilteredListFailure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ArrayList<Index> singlePersonIndexList = new ArrayList<>();
        singlePersonIndexList.add(outOfBoundIndex);
        TagAddCommand tagAddCommand = prepareCommand(singlePersonIndexList,
                new TagAddDescriptor(new PersonBuilder().withATags(VALID_TAG_HUSBAND).build()));

        assertCommandFailure(tagAddCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Adds tag to a person in a filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void executeInvalidPersonIndexFilteredListFailure() {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        ArrayList<Index> singlePersonIndexList = new ArrayList<>();
        singlePersonIndexList.add(outOfBoundIndex);

        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        TagAddCommand tagAddCommand = prepareCommand(singlePersonIndexList,
                new TagAddDescriptor(new PersonBuilder().withATags(VALID_TAG_HUSBAND).build()));

        assertCommandFailure(tagAddCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ArrayList<Index> singlePersonIndexList1 = new ArrayList<>();
        singlePersonIndexList1.add(INDEX_FIRST_PERSON);
        ArrayList<Index> singlePersonIndexList2 = new ArrayList<>();
        singlePersonIndexList2.add(INDEX_SECOND_PERSON);
        final TagAddCommand standardCommand = new TagAddCommand(singlePersonIndexList1, DESC_JAMES);

        TagAddDescriptor copyDescriptor = new TagAddDescriptor(DESC_JAMES);
        TagAddDescriptor copyDescriptor1 = new TagAddDescriptor(DESC_LUCY);
        TagAddCommand commandWithSameValues = new TagAddCommand(singlePersonIndexList1, copyDescriptor);
        // same values -> returns true
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // same object -> returns true
        assertTrue(copyDescriptor.equals(copyDescriptor));

        // null -> returns false
        assertFalse(standardCommand == null);

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new TagAddCommand(singlePersonIndexList2, DESC_JAMES)));

        // different command -> returns false
        assertFalse(standardCommand.equals(new TagAddCommand(singlePersonIndexList1, DESC_LUCY)));

        // different object -> returns false
        assertFalse(copyDescriptor.equals(standardCommand));

        // different descriptor -> returns false
        assertFalse(copyDescriptor.equals(copyDescriptor1));
    }

    @Test
    public void tagAddDescriptorTest()throws Exception {
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(new Tag("Tags"));
        TagAddDescriptor tagAddDescriptor = new TagAddDescriptor();
        tagAddDescriptor.setName(new Name("Name"));
        tagAddDescriptor.setAddress(new Address("Address"));
        tagAddDescriptor.setEmail(new Email("Email@email.com"));
        tagAddDescriptor.setPhone(new Phone("123"));
        tagAddDescriptor.setTags(tagSet);

        TagAddDescriptor toCopy = new TagAddDescriptor(tagAddDescriptor);

        assertTrue(tagAddDescriptor.equals(toCopy));

        assertTrue(tagAddDescriptor.getName().equals(toCopy.getName()));

        assertTrue(tagAddDescriptor.getPhone().equals(toCopy.getPhone()));

        assertTrue(tagAddDescriptor.getAddress().equals(toCopy.getAddress()));

        assertTrue(tagAddDescriptor.getEmail().equals(toCopy.getEmail()));

        assertTrue(tagAddDescriptor.getTags().equals(toCopy.getTags()));
    }

    /**
     * Creates string for edited tag list.
     * @param editedPerson edited person to show tag list
     * Returns formatted string to indicate edited tag list.
     */
    public String createTagListDisplay(Person editedPerson) {
        int tagListStringStartIndex = 1;
        int tagListStringEndIndex;
        String tagChangedDisplayRaw = editedPerson.getTags().toString();
        tagListStringEndIndex = tagChangedDisplayRaw.length() - 1;
        String tagChangedDisplay = editedPerson.getName() + " Tag List: "
                + tagChangedDisplayRaw.substring(tagListStringStartIndex, tagListStringEndIndex);
        return String.format(TagAddCommand.MESSAGE_ADD_TAG_SUCCESS, tagChangedDisplay);
    }

    /**
     * Returns an {@code TagAddCommand} with parameters {@code index} and {@code descriptor}
     */
    private TagAddCommand prepareCommand(ArrayList<Index> index, TagAddDescriptor descriptor) {
        TagAddCommand tagAddCommand = new TagAddCommand(index, descriptor);
        tagAddCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return tagAddCommand;
    }

}
```
###### \java\seedu\address\logic\commands\TagFindCommandTest.java
``` java

/**
 * Contains integration tests (interaction with the Model) for {@code TagFindCommand}.
 */
public class TagFindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new AddressBook(), new UserPrefs());

    @Test
    public void equals() {
        boolean looseFind = true;
        TagMatchingKeywordPredicate firstPredicate =
                new TagMatchingKeywordPredicate("first", looseFind);
        TagMatchingKeywordPredicate secondPredicate =
                new TagMatchingKeywordPredicate("second", looseFind);

        TagFindCommand findFirstCommand = new TagFindCommand(firstPredicate);
        TagFindCommand findSecondCommand = new TagFindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        TagFindCommand findFirstCommandCopy = new TagFindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand == null);

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void executeZeroKeywordNoPersonFound() {
        boolean looseFind = true;
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        TagFindCommand command = prepareCommand(" ", looseFind);
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void executeSinglePersonFound() {
        boolean looseFind = true;
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        TagFindCommand command = prepareCommand("owesMoney", looseFind);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON));
    }

    @Test
    public void executeMultiplePersonsFound() {
        boolean looseFind = true;
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 7);
        TagFindCommand command = prepareCommand("Friends", looseFind);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    /**
     * Parses {@code userInput} into a {@code TagFindCommand}.
     */
    private TagFindCommand prepareCommand(String userInput, boolean looseFind) {
        TagFindCommand command =
                new TagFindCommand(new TagMatchingKeywordPredicate(userInput, looseFind));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(TagFindCommand command, String expectedMessage,
                                      List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
```
###### \java\seedu\address\logic\commands\TagRemoveCommandTest.java
``` java

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class TagRemoveCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new AddressBook(), new UserPrefs());

    @Test
    public void executeTagRemoveSinglePersonFailure() throws Exception {
        showFirstPersonOnly(model);
        ArrayList<Index> singlePersonIndexList = new ArrayList<>();
        singlePersonIndexList.add(INDEX_FIRST_PERSON);

        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Tag> singleTagSet = new HashSet<>();
        Tag onlyTag = new Tag(VALID_TAG_HUSBAND);
        singleTagSet.add(onlyTag);

        TagRemoveDescriptor tagRemoveDescriptor = new TagRemoveDescriptor();
        tagRemoveDescriptor.setTags(singleTagSet);

        Person editedPerson = new PersonBuilder(personInFilteredList).withOutTag(VALID_TAG_HUSBAND).build();
        TagRemoveCommand tagRemoveCommand = prepareCommand(singlePersonIndexList, tagRemoveDescriptor);

        String expectedMessage = String.format(TagRemoveCommand.MESSAGE_TAG_NOT_FOUND, onlyTag.toString());
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new AddressBook(),
                new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandFailure(tagRemoveCommand, model, expectedMessage);
    }

    @Test
    public void executeInvalidPersonIndexUnfilteredListFailure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ArrayList<Index> singlePersonIndexList = new ArrayList<>();
        singlePersonIndexList.add(outOfBoundIndex);
        TagRemoveCommand tagRemoveCommand = prepareCommand(singlePersonIndexList,
                new TagRemoveDescriptor(new PersonBuilder().withOutTag(VALID_TAG_HUSBAND).build()));

        assertCommandFailure(tagRemoveCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeInvalidPersonIndexFilteredListFailure() throws Exception {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        ArrayList<Index> singlePersonIndexList = new ArrayList<>();
        singlePersonIndexList.add(outOfBoundIndex);

        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        TagRemoveCommand tagRemoveCommand = prepareCommand(singlePersonIndexList,
                new TagRemoveDescriptor(new PersonBuilder().withOutTag(VALID_TAG_HUSBAND).build()));

        assertCommandFailure(tagRemoveCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ArrayList<Index> singlePersonIndexList1 = new ArrayList<>();
        singlePersonIndexList1.add(INDEX_FIRST_PERSON);
        ArrayList<Index> singlePersonIndexList2 = new ArrayList<>();
        singlePersonIndexList2.add(INDEX_SECOND_PERSON);
        final TagRemoveCommand standardCommand = new TagRemoveCommand(singlePersonIndexList1, DESCR_JAMES);

        // same values -> returns true
        TagRemoveDescriptor copyDescriptor = new TagRemoveDescriptor(DESCR_JAMES);
        TagRemoveCommand commandWithSameValues = new TagRemoveCommand(singlePersonIndexList1, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand == null);

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new TagRemoveCommand(singlePersonIndexList2, DESCR_JAMES)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new TagRemoveCommand(singlePersonIndexList1, DESCR_LUCY)));
    }

    @Test
    public void tagRemoveDescriptorTest()throws Exception {
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(new Tag("Tags"));
        TagRemoveDescriptor tagRemoveDescriptor = new TagRemoveDescriptor();
        tagRemoveDescriptor.setName(new Name("Name"));
        tagRemoveDescriptor.setAddress(new Address("Address"));
        tagRemoveDescriptor.setEmail(new Email("Email@email.com"));
        tagRemoveDescriptor.setPhone(new Phone("123"));
        tagRemoveDescriptor.setTags(tagSet);

        TagRemoveDescriptor toCopy = new TagRemoveDescriptor(tagRemoveDescriptor);

        assertTrue(tagRemoveDescriptor.equals(tagRemoveDescriptor));

        assertTrue(tagRemoveDescriptor.equals(toCopy));

        assertTrue(tagRemoveDescriptor.getName().equals(toCopy.getName()));

        assertTrue(tagRemoveDescriptor.getPhone().equals(toCopy.getPhone()));

        assertTrue(tagRemoveDescriptor.getAddress().equals(toCopy.getAddress()));

        assertTrue(tagRemoveDescriptor.getEmail().equals(toCopy.getEmail()));

        assertTrue(tagRemoveDescriptor.getTags().equals(toCopy.getTags()));

        assertFalse(tagRemoveDescriptor == null);
    }

    @Test
    public void createEditedPersonTestSuccess() throws Exception {
        showFirstPersonOnly(model);
        ArrayList<Index> singlePersonIndexList = new ArrayList<>();
        singlePersonIndexList.add(INDEX_FIRST_PERSON);

        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Tag> singleTagSet = new HashSet<Tag>();
        Tag onlyTag = new Tag(VALID_TAG_HUSBAND);
        singleTagSet.add(onlyTag);

        TagRemoveDescriptor tagRemoveDescriptor = new TagRemoveDescriptor(personInFilteredList);
        tagRemoveDescriptor.setTags(singleTagSet);
        TagRemoveCommand tagRemoveCommand = new TagRemoveCommand(singlePersonIndexList, tagRemoveDescriptor);
        Person person = tagRemoveCommand.createEditedPerson(personInFilteredList, tagRemoveDescriptor);

        assertTrue(person.getName().equals(personInFilteredList.getName()));
        assertTrue(person.getPhone().equals(personInFilteredList.getPhone()));
        assertTrue(person.getAddress().equals(personInFilteredList.getAddress()));
        assertTrue(person.getEmail().equals(personInFilteredList.getEmail()));
        assertTrue(person.getTags().equals(tagRemoveDescriptor.getTags()));
    }

    @Test
    public void containsTagTestSuccess()throws Exception {
        ArrayList<Index> singlePersonIndexList = new ArrayList<>();
        singlePersonIndexList.add(INDEX_FIRST_PERSON);
        Set<Tag> tagSet = new HashSet<>();
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag(VALID_TAG_FRIEND));
        tagSet.add(new Tag(VALID_TAG_HUSBAND));
        tagSet.add(new Tag(VALID_TAG_FRIEND));
        TagRemoveDescriptor tagRemoveDescriptor = new TagRemoveDescriptor();
        tagRemoveDescriptor.setTags(tagSet);
        TagRemoveCommand tagRemoveCommand = new TagRemoveCommand(singlePersonIndexList, tagRemoveDescriptor);
        assertTrue(tagRemoveCommand.containsTag(tagList));
    }

    @Test
    public void containsTagTestFail()throws Exception {
        ArrayList<Index> singlePersonIndexList = new ArrayList<>();
        singlePersonIndexList.add(INDEX_FIRST_PERSON);
        Set<Tag> tagSet = new HashSet<>();
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag(VALID_TAG_HUSBAND));
        tagSet.add(new Tag(VALID_TAG_FRIEND));
        TagRemoveDescriptor tagRemoveDescriptor = new TagRemoveDescriptor();
        tagRemoveDescriptor.setTags(tagSet);
        TagRemoveCommand tagRemoveCommand = new TagRemoveCommand(singlePersonIndexList, tagRemoveDescriptor);
        assertFalse(tagRemoveCommand.containsTag(tagList));
    }

    @Test
    public void makeFullIndexListTestSuccess()throws Exception {
        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        List<ReadOnlyPerson> personList = new ArrayList<>();
        personList.add(personInFilteredList);

        ArrayList<Index> singlePersonIndexList = new ArrayList<>();
        singlePersonIndexList.add(INDEX_FIRST_PERSON);
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(new Tag(VALID_TAG_HUSBAND));
        tagSet.add(new Tag(VALID_TAG_FRIEND));
        TagRemoveDescriptor tagRemoveDescriptor = new TagRemoveDescriptor();
        tagRemoveDescriptor.setTags(tagSet);
        TagRemoveCommand tagRemoveCommand = new TagRemoveCommand(singlePersonIndexList, tagRemoveDescriptor);
        ArrayList<Index> indexList = tagRemoveCommand.makeFullIndexList(personList.size());
        assertTrue(indexList.size() == personList.size());
    }

    @Test
    public void createModifiableTagSetTest()throws Exception {
        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Tag> originalTagSet = personInFilteredList.getTags();
        List<ReadOnlyPerson> personList = new ArrayList<>();
        personList.add(personInFilteredList);

        ArrayList<Index> singlePersonIndexList = new ArrayList<>();
        singlePersonIndexList.add(INDEX_FIRST_PERSON);
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(new Tag(VALID_TAG_HUSBAND));
        tagSet.add(new Tag(VALID_TAG_FRIEND));
        TagRemoveDescriptor tagRemoveDescriptor = new TagRemoveDescriptor();
        tagRemoveDescriptor.setTags(tagSet);
        TagRemoveCommand tagRemoveCommand = new TagRemoveCommand(singlePersonIndexList, tagRemoveDescriptor);
        Set<Tag> tagSetCopy = tagRemoveCommand.createModifiableTagSet(originalTagSet, new Tag("tag"));
        assertTrue(tagSetCopy.equals(originalTagSet));
    }

    /**
     * Returns an {@code TagRemoveCommand} with parameters {@code index} and {@code descriptor}
     */
    private TagRemoveCommand prepareCommand(ArrayList<Index> index, TagRemoveDescriptor descriptor) {
        TagRemoveCommand tagRemoveCommand = new TagRemoveCommand(index, descriptor);
        tagRemoveCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return tagRemoveCommand;
    }
}
```
###### \java\seedu\address\logic\parser\SwitchThemeCommandParserTest.java
``` java

public class SwitchThemeCommandParserTest {
    private SwitchThemeCommandParser parser = new SwitchThemeCommandParser();

    @Test
    public void parseValidArgsReturnsSwitchThemeCommand() {
        String themeChoice1 = SwitchThemeCommand.DARK_THEME_WORD3;
        String themeChoice2 = SwitchThemeCommand.DARK_THEME_WORD1;
        String themeChoice3 = SwitchThemeCommand.DARK_THEME_WORD2;

        SwitchThemeCommand expectedSwitchThemeCommandOne =
                new SwitchThemeCommand(themeChoice1);
        assertParseSuccess(parser, themeChoice1, expectedSwitchThemeCommandOne);

        SwitchThemeCommand expectedSwitchThemeCommandTwo =
                new SwitchThemeCommand(themeChoice2);
        assertParseSuccess(parser, themeChoice2, expectedSwitchThemeCommandTwo);

        SwitchThemeCommand expectedSwitchThemeCommandThree =
                new SwitchThemeCommand(themeChoice3);
        assertParseSuccess(parser, themeChoice3, expectedSwitchThemeCommandThree);
    }

    @Test
    public void parseEmptyArgThrowsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwitchThemeCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\TagAddCommandParserTest.java
``` java

public class TagAddCommandParserTest {

    private static final String SPACE = " ";
    private static final String TAG_EMPTY = " ";

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagAddCommand.MESSAGE_USAGE);

    private TagAddCommandParser parser = new TagAddCommandParser();

    @Test
    public void parseMissingPartsFailure() {
        // no index specified
        assertParseFailure(parser, VALID_TAG_FRIEND, MESSAGE_INVALID_FORMAT);

        // no tag input
        assertParseFailure(parser, "1 2", MESSAGE_INVALID_FORMAT);

        // no tag specified
        assertParseFailure(parser, TAG_EMPTY, MESSAGE_INVALID_FORMAT);

        // no user input
        assertParseFailure(parser, SPACE, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parseTagAddSuccess() throws Exception {
        Index targetIndex = INDEX_SECOND_PERSON;
        ArrayList<Index> singlePersonIndexList = new ArrayList<>();
        singlePersonIndexList.add(targetIndex);

        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(new Tag(VALID_TAG_FRIEND));

        String userInput = targetIndex.getOneBased() + SPACE + VALID_TAG_FRIEND;
        TagAddCommand.TagAddDescriptor descriptor = new TagAddCommand.TagAddDescriptor();
        descriptor.setTags(tagSet);
        TagAddCommand expectedCommand = new TagAddCommand(singlePersonIndexList, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
```
###### \java\seedu\address\logic\parser\TagFindCommandParserTest.java
``` java

public class TagFindCommandParserTest {

    private TagFindCommandParser parser = new TagFindCommandParser();

    @Test
    public void parseEmptyArgThrowsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagFindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseValidArgsReturnsTagFindCommand() {
        boolean looseFind = true;
        TagFindCommand expectedTagFindCommandOne =
                new TagFindCommand(new TagMatchingKeywordPredicate("friends", true));
        assertParseSuccess(parser, "friends", expectedTagFindCommandOne);
        TagFindCommand expectedTagFindCommandTwo =
                new TagFindCommand(new TagMatchingKeywordPredicate("friend 2 be", true));
        assertParseSuccess(parser, "friend 2 be", expectedTagFindCommandTwo);
        TagFindCommand expectedTagFindCommandThree =
                new TagFindCommand(new TagMatchingKeywordPredicate("1 2 3", true));
        assertParseSuccess(parser, "1 2 3", expectedTagFindCommandThree);
    }

}
```
###### \java\seedu\address\logic\parser\TagRemoveCommandParserTest.java
``` java

public class TagRemoveCommandParserTest {

    private static final String TAG_EMPTY = " ";

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagRemoveCommand.MESSAGE_USAGE);

    private TagRemoveCommandParser parser = new TagRemoveCommandParser();

    @Test
    public void parseMissingPartsFailure() {

        // no user input
        assertParseFailure(parser, TAG_EMPTY, MESSAGE_INVALID_FORMAT);
        // no tagName provided
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parseTagRemoveSuccess() throws Exception {
        Index targetIndex = INDEX_SECOND_PERSON;
        ArrayList<Index> singlePersonIndexList = new ArrayList<>();
        singlePersonIndexList.add(targetIndex);

        Set<Tag> tagSet = new HashSet<Tag>();
        tagSet.add(new Tag(VALID_TAG_FRIEND));

        String userInput = targetIndex.getOneBased() + " " + VALID_TAG_FRIEND;
        TagRemoveCommand.TagRemoveDescriptor descriptor = new TagRemoveDescriptor();
        descriptor.setTags(tagSet);
        TagRemoveCommand expectedCommand = new TagRemoveCommand(singlePersonIndexList, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
```
