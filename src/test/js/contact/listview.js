var $ = require("jquery");
var React = require('react');
var ReactDOM = require('react-dom');

var ListView = React.createClass({
  getInitialState: function() {
    return {
      list: []
    };
  },
  componentDidMount: function() {
    this.loadList();
  },
  loadList: function() {
    $.ajax({
      url: "/testContact/list",
      method: "get",
      dataType: 'json',
      cache: false,
      success: function(list) {
        this.setState({list: list});
      }.bind(this),
      error: function(xhr, status, err) {
        //console.error(this.props.url, status, err.toString());
        alert("Error when retrieve list!");
      }.bind(this)
    });
  },
  render: function() {
    return (
      <div id="ListView">
        <BtnAreaInListView showEdit={this.props.showEdit} />
        <DataTable list={this.state.list} showEdit={this.props.showEdit} refresh={this.loadList} />
      </div>
    );
  }
});

var BtnAreaInListView = React.createClass({
  handleAdd: function() {
    this.props.showEdit(true);
  },
  render: function() {
    return (
      <div>
        <button onClick={this.handleAdd}>Add</button>
      </div>
    );
  }
});

var DataTable = React.createClass({
  render: function() {
    var showEdit = this.props.showEdit
    var refresh = this.props.refresh
    var rows = this.props.list.map(function(item) {
      return (
        <DataRow key={item.id} data={item} showEdit={showEdit} refresh={refresh} />
      );
    });

    return (
      <table>
        <thead>
          <tr>
            <th>name</th>
            <th>phone</th>
            <th>operation</th>
          </tr>
        </thead>
        <tbody>
          {rows}
        </tbody>
      </table>
    );
  }
});

var DataRow = React.createClass({
  render: function() {
    var item = this.props.data
    return (
      <tr>
        <td>{item.name}</td>
        <td>{item.phone}</td>
        <OperationsTd data={item} showEdit={this.props.showEdit} refresh={this.props.refresh} />
      </tr>
    );
  }
});

var OperationsTd = React.createClass({
  handleEdit: function(e) {
    this.props.showEdit(true, this.props.data)
  },
  handleDelete: function(e) {
    $.ajax({
      url: "/testContact/delete/" + this.props.data.id,
      method: "delete",
      success: function() {
//        window.location.reload();
        this.props.refresh();
      }.bind(this)
    });
  },
  render: function() {
    return (
      <td>
        <a href="javascript:;" onClick={this.handleEdit} style={{marginRight: "20px"}} >edit</a>
        <a href="javascript:;" onClick={this.handleDelete}>delete</a>
      </td>
    );
  }
});

module.exports = ListView