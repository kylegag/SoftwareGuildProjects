var globalItems = [];
var currentBalance = 0.00;
var messages;


$(document).ready(function() {
  messages = $('#messages');
  loadItemsAndWireItemButtons();
  wireCalculateButtons();
  wirePurchaseButton();
  wireChangeReturn();
});

function wireChangeReturn() {
  var changeButton = $('#change-button');
  var changeInput = $('#change');
  var currCash = $('#total-cash');
  var item = $('#curr-item');

  changeButton.click(function(event) {
    if (currCash.val().slice(1) === '') {
      return;
    } else {
      currentBalance = 0;
      changeInput.val(currCash.val());
      currCash.val('');
      messages.val('');
      item.val('');
    }
  });

}

function wirePurchaseButton() {
  var purchaseButton = $('#purchase-button');
  var currItem = $('#curr-item');

  purchaseButton.click(function(event) {
    var currDisplay = currItem.val();
    var currCash;

    if ($('#total-cash').val() === '') {
      currCash = 0.0;
    } else {
      currCash = $('#total-cash').val().slice(1);
    }

    if (currDisplay === '') {
      messages.val('Please choose an item.');
      return;
    } else {
      globalItems.forEach(function(item, index) {
        if (currDisplay === item.name) {
          $.ajax({
            type: 'PUT',
            url: 'http://localhost:8080/item/' + item.name + '/' + currCash,
            headers: {
              'Accept': 'application/json',
              'Content-Type': 'application/json'
            },
            'dataType': 'json',
            success: function(change) {
              var cash = 0;
              cash += change.quarters * .25;
              cash += change.dimes * .10;
              cash += change.nickels * .05;
              cash += change.pennies * .01;
              cash = (Math.round(cash * 100) / 100).toFixed(2);
              $('#change').val('$' + cash);
              messages.val('Thank you!!!');

              currItem.val('');
              currentBalance = 0;
              $('#total-cash').val('');
              resetItemButtons();
              loadItemsAndWireItemButtons();
            },
            error: function(xhr) {
              if (xhr.status === 400) {
                var difference = item.price - currentBalance;
                difference = (Math.round(difference * 100) / 100).toFixed(2);
                messages.val('Please insert: $' + difference);
              } else if (xhr.status === 410) {
                messages.val('Sold out!!');
              } else if (xhr.status === 500) {
                messages.val('Internal database error.');
              } else {
                messages.val('Unknown error.');
              }
            }
          });
        }
      });
    }
  });
}


function resetItemButtons() {
  globalItems.forEach(function(item, index) {
    $('#item-button-' + index).unbind();
  });
  globalItems = [];
  $('#vendingItems').empty();
}



function loadItemsAndWireItemButtons() {
  var vendingItems = $('#vendingItems');

  $.ajax({
    type: 'GET',
    url: 'http://localhost:8080/items',
    success: function(itemArray) {
      $.each(itemArray, function(index, item) {
        var button = '<button type="button"';
        button += ' id="item-button-' + index + '"';
        button += ' class="btn btn-default">';
        button += item.name;
        button += '<br>$' + (Math.round(item.price * 100) / 100).toFixed(2);
        button += '<br>Current stock: ' + item.stock;
        button += '</button>';

        globalItems.push(item);
        vendingItems.append(button);
      });

      wireItemButtons();
    },
    error: function() {}
  });
}

function wireCalculateButtons() {
  var balanceDisplay = $('#total-cash');

  $('#add-dollar-button').click(function(event) {
    currentBalance += 1.00;
    balanceDisplay.val('$' + (Math.round(currentBalance * 100) / 100).toFixed(2));
  });
  $('#add-quarter-button').click(function(event) {
    currentBalance += .25;
    balanceDisplay.val('$' + (Math.round(currentBalance * 100) / 100).toFixed(2));
  });
  $('#add-dime-button').click(function(event) {
    currentBalance += .10;
    balanceDisplay.val('$' + (Math.round(currentBalance * 100) / 100).toFixed(2));
  });
  $('#add-nickel-button').click(function(event) {
    currentBalance += .05;
    balanceDisplay.val('$' + (Math.round(currentBalance * 100) / 100).toFixed(2));
  });
}

function wireItemButtons() {
  var currItem = $('#curr-item');

  globalItems.forEach(function(item, index) {
    $('#item-button-' + index).click(function(event) {
      currItem.val(item.name);
    });
  });
}

/*
$(document).ready(function() {
  loadContacts();

  $('#add-button').click(function(event) {

    var haveValidationErrors = checkAndDisplayValidationErrors($('#add-form').find('input'));

    if (haveValidationErrors) {
      return false;
    }

    $.ajax({
      type: 'POST',
      url: 'http://localhost:8080/contact',
      data: JSON.stringify({
        firstName: $('#add-first-name').val(),
        lastName: $('#add-last-name').val(),
        company: $('#add-company').val(),
        phone: $('#add-phone').val(),
        email: $('#add-email').val(),
      }),
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      'dataType': 'json',
      success: function() {
        $('#errorMessages').empty();
        $('#add-first-name').val('');
        $('#add-last-name').val('');
        $('#add-company').val('');
        $('#add-phone').val('');
        $('#add-email').val('');
        loadContacts();
      },
      error: function() {
        $('#errorMessages')
          .append($('<li>'))
          .attr({
            class: 'list-group-item list-group-item-danger'
          })
          .text('Error calling web service. Please try again later.')
      }
    })
  });

  $('#edit-button').click(function(event) {
    $.ajax({
      type: 'PUT',
      url: 'http://localhost:8080/contact/' + $('#edit-contact-id').val(),
      data: JSON.stringify({
        contactId: $('#edit-contact-id').val(),
        firstName: $('#edit-first-name').val(),
        lastName: $('#edit-last-name').val(),
        company: $('#edit-company').val(),
        phone: $('#edit-phone').val(),
        email: $('#edit-email').val()
      }),
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      'dataType': 'json',
      success: function() {
        $('#errorMessage').empty();
        hideEditForm();
        loadContacts();
      },
      error: function() {
        $('#errorMessages')
          .append($('<li>')
            .attr({
              class: 'list-group-item list-group-item-danger'
            })
            .text('Error calling web service. Please try again later'));
      }
    })
  });
});

function loadContacts() {

  clearContactTable();

  var contentRows = $('#contentRows');

  $.ajax({
    type: 'GET',
    url: 'http://localhost:8080/contacts',
    success: function(contactArray) {
      $.each(contactArray, function(index, contact) {
        var name = contact.firstName + ' ' + contact.lastName;
        var company = contact.company;
        var contactId = contact.contactId;

        var row = '<tr>';
        row += '<td>' + name + '</td>';
        row += '<td>' + company + '</td>';
        row += '<td><a onclick="showEditForm(' + contactId + ')">Edit</a></td>';
        row += '<td><a onclick="deleteContact(' + contactId + ')">Delete</a></td>';
        row += '</tr>';

        contentRows.append(row);
      });
    },
    error: function() {
      $('#errorMessages')
        .append($('<li>')
          .attr({
            class: 'list-group-item list-group-item-danger'
          })
          .text('Error calling web service. Please try again later.'));
    }
  });
}

function clearContactTable() {
  $('#contentRows').empty();
}

function showEditForm(contactId) {
  $('#errorMessages').empty();

  $.ajax({
    type: 'GET',
    url: 'http://localhost:8080/contact/' + contactId,
    success: function(data, status) {
      $('#edit-first-name').val(data.firstName);
      $('#edit-last-name').val(data.lastName);
      $('#edit-company').val(data.company);
      $('#edit-phone').val(data.phone);
      $('#edit-email').val(data.email);
      $('#edit-contact-id').val(data.contactId);
    },
    error: function() {

      $('#errorMessages')
        .append($('<li>')
          .attr({
            class: 'list-group-item list-group-item-danger'
          })
          .text('Error calling web service. Please try again later.'));
    }
  })

  $('#contactTableDiv').hide();
  $('#editFormDiv').show();
}

function hideEditForm() {
  $('#errorMessages').empty();

  $('#edit-first-name').val('');
  $('#edit-last-name').val('');
  $('#edit-company').val('');
  $('#edit-phone').val('');
  $('#edit-email').val('');

  $('#editFormDiv').hide();
  $('#contactTableDiv').show();
}

function deleteContact(contactId) {
  $.ajax({
    type: 'DELETE',
    url: 'http://localhost:8080/contact/' + contactId,
    success: function() {
      loadContacts();
    }
  });
}

function checkAndDisplayValidationErrors(input) {
  $('#errorMessages').empty();

  var errorMessages = [];

  input.each(function() {
    if (!this.validity.valid) {
      var errorField = $('label[for=' + this.id + ']').text();
      errorMessages.push(errorField + ' ' + this.validationMessage);
    }
  });

  if (errorMessages.length > 0) {
    $.each(errorMessages, function(index, message) {
      $('#errorMessages').append($('<li>').attr({
        class: 'list-group-item list-group-item-danger'
      }).text(message));
    });

    return true;
  } else {
    return false;
  }
}

*/