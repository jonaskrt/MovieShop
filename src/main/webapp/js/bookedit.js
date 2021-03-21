/**
 * view-controller for bookedit.html
 *
 * M133: Bookshelf
 *
 * @author  Marcel Suter
 */

/**
 * register listeners and load the book data
 */
$(document).ready(function () {
    loadPublishers();
    loadBook();

    /**
     * listener for submitting the form
     */


    /**
     * listener for button [abbrechen], redirects to bookshelf
     */

});

/**
 *  loads the data of this book
 *
 */
function loadBook() {

}

/**
 * shows the data of this book
 * @param  book  the book data to be shown
 */
function showBook(book) {

}

/**
 * sends the book data to the webservice
 * @param form the form being submitted
 */
function saveBook(form) {
    form.preventDefault();
}

function loadPublishers() {
    $
        .ajax({
            url: "./resource/publisher/list",
            dataType: "json",
            type: "GET"
        })
        .done(showPublishers)
        .fail(function (xhr, status, errorThrown) {
            if (xhr.status == 404) {
                $("#message").text("Kein Verlag gefunden");
            } else {
                window.location.href = "./bookshelf.html";
            }
        })
}

function showPublishers(publishers) {

    $.each(publishers, function (uuid, publisher) {
        $('#publisher').append($('<option>', {
            value: publisher.publisherUUID,
            text : publisher.publisher
        }));
    });
}
