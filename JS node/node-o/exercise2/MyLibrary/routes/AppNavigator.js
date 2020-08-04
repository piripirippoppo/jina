import {createAppContainer} from 'react-navigation';
import {createStackNavigator} from 'react-navigation-stack';
import { createBottomTabNavigator } from 'react-navigation-tabs';
import AddAuthor from './../screens/AddAuthor';
import Author from './../screens/Author';
import Authors from './../screens/Authors';
import { colors } from '../theme';

const options = {
  navigationOptions: {
    headerStyle: {
      backgroundColor: colors.primary
    },
  }
}

const AuthorsStack = createStackNavigator({
  Authors: Authors,
  Author: Author
},options)

const TabNavigator = createBottomTabNavigator({
  Authors: AuthorsStack,
  AddAuthor: AddAuthor
})


const App = createAppContainer(TabNavigator);
export default App;
