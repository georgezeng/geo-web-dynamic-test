import React from 'react'
import {connect} from 'react-redux'
import List from './list'
import Edit from './edit'

let View = ({place}) => {
  if(place == "list") {
    return <List />;
  }
  return <Edit />;
}

const mapStateToProps = (reducedState, props) => {
  return {
    place: reducedState.view.place
  }
}

View = connect(mapStateToProps)(View)

const App = () => (
  <div>
    <h2>Contact CRUD</h2>
    <View />
  </div>
)

export default App