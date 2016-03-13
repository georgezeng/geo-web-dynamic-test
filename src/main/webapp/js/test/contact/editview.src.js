var $ = require("jquery");
var JSON = require("json3");
var React = require('react');
var ReactDOM = require('react-dom');

var EditView = React.createClass({
  onUpdate: function() {
    this.props.onUpdate({
      id: this.props.data.id,
      name: this.refs.name.value,
      phone: this.refs.phone.value
    })
  },
  render: function() {
    return (
      <div id="EditView">
        <BtnAreaInEditView showEdit={this.props.showEdit} data={this.props.data} />
        <div>
          <input type="hidden" value={this.props.data.id} />
        </div>
        <div>
          <input ref="name" value={this.props.data.name} onChange={this.onUpdate} />
        </div>
        <div>
          <input ref="phone" value={this.props.data.phone} onChange={this.onUpdate} />
        </div>
      </div>
    );
  }
});

var BtnAreaInEditView = React.createClass({
  handleBack: function() {
    this.props.showEdit(false);
  },
  handleSave: function() {
    $.ajax({
      url: "/testContact/save",
      contentType: "application/json",
      method: "post",
      dataType: 'json',
      data: JSON.stringify(this.props.data),
      cache: false,
      success: function(list) {
        this.props.showEdit(false);
      }.bind(this),
      error: function(xhr, status, err) {
        alert("Error when save!");
      }
    });
  },
  render: function() {
    return (
      <div>
        <button onClick={this.handleBack}>Back</button>
        <button onClick={this.handleSave}>Save</button>
      </div>
    );
  }
});

module.exports = EditView