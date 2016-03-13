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
        <DataRow data={item} showEdit={showEdit} refresh={refresh} />
      );
    });

    return (
      <table>
        <thead>
          <th>name</th>
          <th>phone</th>
          <th>operation</th>
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
        <a href="javascript:;" onClick={this.handleEdit}>edit</a>&nbsp;&nbsp;&nbsp;
        <a href="javascript:;" onClick={this.handleDelete}>delete</a>
      </td>
    );
  }
});

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

ReactDOM.render(
  <Content />,
  document.getElementById("content")
);
