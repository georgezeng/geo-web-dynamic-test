var React = require('react');
var ReactDOM = require('react-dom');
var ListView = require("./listview.js");
var EditView = require("./editview.js");


var Content = React.createClass({
  getInitialState: function() {
    return { showEdit: false };
  },
  showEdit: function(toShow, data) {
    if(toShow && !data) {
      data = {
        id: "",
        name: "",
        phone: ""
      }
    }
    this.setState({showEdit: toShow, data: data});
  },
  onUpdate: function(data) {
    this.showEdit(true, data)
  },
  render: function() {
    if(!this.state.showEdit) {
      return (<ListView showEdit={this.showEdit} />);
    } else {
      return (<EditView showEdit={this.showEdit} data={this.state.data} onUpdate={this.onUpdate} />);
    }
  }
});



ReactDOM.render(
  <Content />,
  document.getElementById("content")
);
