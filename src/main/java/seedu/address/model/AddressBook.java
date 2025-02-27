package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.client.Client;
import seedu.address.model.client.UniqueClientList;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.NonOverlappingMeetingList;


/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameClient comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueClientList clients;
    private final NonOverlappingMeetingList meetings;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        clients = new UniqueClientList();
        meetings = new NonOverlappingMeetingList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Clients in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the client list with {@code clients}.
     * {@code clients} must not contain duplicate clients.
     */
    public void setClients(List<Client> clients) {
        this.clients.setClients(clients);
    }

    /**
     * Replaces the contents of the meeting list with {@code meetings}.
     * {@code clients} must not contain overlapping meetings.
     */
    public void setMeetings(List<Meeting> meetings) {
        this.meetings.setMeetings(meetings);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setClients(newData.getClientList());
        setMeetings(newData.getMeetingList());
    }

    //// client-level operations

    /**
     * Returns true if a client with the same identity as {@code client} exists in the address book.
     */
    public boolean hasClient(Client client) {
        requireNonNull(client);
        return clients.contains(client);
    }

    /**
     * Adds a client to the address book.
     * The client must not already exist in the address book.
     */
    public void addClient(Client p) {
        clients.add(p);
    }

    /**
     * Replaces the given client {@code target} in the list with {@code editedClient}.
     * {@code target} must exist in the address book.
     * The client identity of {@code editedClient} must not be the same as another existing client in the address book.
     */
    public void setClient(Client target, Client editedClient) {
        requireNonNull(editedClient);

        clients.setClient(target, editedClient);
        meetings.setClient(target, editedClient);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeClient(Client key) {
        removeClientMeetings(key);
        clients.remove(key);
    }

    private void removeClientMeetings(Client key) {
        for (Iterator<Meeting> iterator = meetings.iterator(); iterator.hasNext();) {
            Meeting m = iterator.next();
            if (m.getClient().equals(key)) {
                iterator.remove();
            }
        }
    }

    /**
     * Returns true if a meeting overlaps with {@code meeting} in the address book.
     */
    public boolean isOverlapping(Meeting meeting) {
        requireNonNull(meeting);
        return meetings.overlaps(meeting);
    }

    /**
     * Returns true if a meeting overlaps with {@code meeting} in the address book except the specified meeting.
     */
    public boolean isOverlappingExcept(Meeting meeting, Meeting exceptedMeeting) {
        requireAllNonNull(meeting, exceptedMeeting);
        return meetings.overlapsExcept(meeting, exceptedMeeting);
    }

    /**
     * Adds a meeting to the address book.
     * The meeting must not be overlapping with other meetings in the address book.
     */
    public void addMeeting(Meeting meeting) {
        meetings.add(meeting);
    }

    /**
     * Removes {@code meeting} from this {@code AddressBook}.
     * {@code meeting} must exist in the address book.
     */
    public void removeMeeting(Meeting meeting) {
        meetings.remove(meeting);
    }

    /**
     * Replaces the given meeting {@code target} with {@code editedMeeting}.
     * {@code target} must exist in the address book.
     * The meeting timing of {@code editedMeeting} must not be overlapping with
     * another existing meeting in the address book.
     */
    public void setMeeting(Meeting target, Meeting editedMeeting) {
        requireNonNull(editedMeeting);

        meetings.setMeeting(target, editedMeeting);
    }

    /**
     * Sorts meetings in ascending order.
     */
    public void sortMeetings() {
        meetings.sortAscending();
    }

    //// util methods

    @Override
    public String toString() {
        return clients.asUnmodifiableObservableList().size() + " clients";
        // TODO: refine later
    }

    @Override
    public ObservableList<Client> getClientList() {
        return clients.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Meeting> getMeetingList() {
        return meetings.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && clients.equals(((AddressBook) other).clients));
    }

    @Override
    public int hashCode() {
        return clients.hashCode();
    }
}
