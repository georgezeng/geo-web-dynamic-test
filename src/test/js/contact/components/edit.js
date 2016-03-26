import React from 'react'
import { connect } from 'react-redux'
import * as Actions from '../actions'

let Edit = ({dispatch, item = {id:null, name: "", phone:""}}) => {
  let nameInput, phoneInput
  return (
    <div>
      <div>
        <button onClick={() => {dispatch(Actions.list())}}>Back</button>
        <button onClick={() => {
          item.name = nameInput.value.trim()
          item.phone = phoneInput.value.trim()
          dispatch(Actions.save(item))
        }}>Save</button>
      </div>
      <div>
        <input ref={element => {if(element) {nameInput = element; element.value = item.name;}}} />
      </div>
      <div>
        <input ref={element => {if(element) {phoneInput = element; element.value = item.phone;}}} />
      </div>
    </div>
  )
}

const mapStateToProps = (reducedState) => {
  return {
    item: reducedState.view.item
  }
}

Edit = connect(mapStateToProps)(Edit)

export default Edit