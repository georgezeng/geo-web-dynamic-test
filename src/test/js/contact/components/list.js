import $ from 'jquery'
import React, {Component} from 'react'
import {connect} from 'react-redux'
import * as Actions from '../actions'

class List extends Component {
  componentDidMount() {
    const { dispatch } = this.props
    dispatch(Actions.fetchList())
  }

  render() {
    const {dispatch, list} = this.props
    return (
      <div>
        <div>
          <button onClick={() => {dispatch(Actions.add())}}>Add</button>
        </div>
        <table cellSpacing={1} cellPadding={5}>
          <thead>
            <tr>
              <th>name</th>
              <th>phone</th>
              <th>operation</th>
            </tr>
          </thead>
          <tbody>
            {
              list.map(item =>
                <tr>
                  <td>{item.name}</td>
                  <td>{item.phone}</td>
                  <td>
                    <a href="javascript:;" onClick={() => {dispatch(Actions.edit(item))}}>edit</a>{' '}
                    <a href="javascript:;" onClick={() => {dispatch(Actions.remove(item))}}>delete</a>
                  </td>
                </tr>
              )
            }
          </tbody>
        </table>
      </div>
      )
    }
}

const mapStateToProps = (reducedState) => {
  return {
    list: reducedState.list
  }
}

List = connect(mapStateToProps)(List)

export default List