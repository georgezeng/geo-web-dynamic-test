import { combineReducers } from 'redux'

const list = (state = [], action) => {
  if(action.list) {
    return action.list;
  } else return state;
}

const view = (state, action) => {
  switch(action.type) {
    case "add":
    case "edit":
      return {place: "edit", item: action.item};
    default: return {place: "list"};
  }
}

const reducers = combineReducers({
  view,
  list
})

export default reducers