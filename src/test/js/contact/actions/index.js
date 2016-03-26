import $ from 'jquery'
import JSON from 'json3'

export const list = (list = []) => {
  return {
    type: 'list',
    list
  }
}

export function fetchList() {
  return (dispatch) => {
    $.ajax({
      url: "/testContact/list",
      method: "get",
      dataType: 'json',
      cache: false,
      success: function(data) {
        dispatch(list(data))
      },
      error: function(xhr, status, err) {
        alert("Error when retrieve list!");
      }
    });
  }
}

export const add = () => {
  return {
    type: 'add'
  }
}

export const edit = (item) => {
  return {
    type: 'edit',
    item
  }
}

export function save(item) {
  return (dispatch) => {
     $.ajax({
       url: "/testContact/save",
       contentType: "application/json",
       method: "post",
       dataType: 'json',
       data: JSON.stringify(item),
       cache: false,
       success: function() {
         dispatch(list())
       },
       error: function(xhr, status, err) {
         alert("Error when save!");
       }
     });
   }
}

export function remove(item) {
  return (dispatch) => {
    $.ajax({
      url: "/testContact/delete/" + item.id,
      method: "delete",
      success: function() {
        dispatch(fetchList())
      },
      error: function(xhr, status, err) {
        alert("Error when delete!");
      }
    });
  }
}


