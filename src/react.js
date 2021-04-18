import {findNodeHandle} from 'react-native';

function view(component) {
  return {
    type: 'React.View',
    value: findNodeHandle(component)
  };
}

module.exports = {
  view: view
};
