package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TagAddCommand;
import seedu.address.model.tag.Tag;

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

        // invalid index input
        assertParseFailure(parser, VALID_TAG_FRIEND + SPACE + VALID_TAG_FRIEND, MESSAGE_INVALID_FORMAT);

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

        String userInput = VALID_TAG_FRIEND + SPACE + targetIndex.getOneBased();
        TagAddCommand.TagAddDescriptor descriptor = new TagAddCommand.TagAddDescriptor();
        descriptor.setTags(tagSet);
        TagAddCommand expectedCommand = new TagAddCommand(singlePersonIndexList, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    /*
    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }*/
}
