import React from 'react';
import {StyleSheet, Text, View, Button, TouchableWithoutFeedback, ScrollView } from 'react-native';
import CenterMessage from '../components/CenterMessage';
import { colors } from './../theme';

export default class Authors extends React.Component {
  static navigationOptions = {
    headerStyle: {
      backgroundColor: colors.primary
    },
    headerTintColor: '#fff',
    title: 'Authors', 
    headerTitleStyle: {
      color: 'white',
      fontSize: 20, 
      fontWeight: '400'
    }
  }
  navigate = (item) => {
    this.props.navigation.navigate('Author', { author: item })
  }
  render() {
    const { screenProps: { authors } } = this.props
    return (
      <ScrollView contentContainerStyle={[!authors.length && { flex: 1 }]}> 
        <View style={[!authors.length &&
        { justifyContent: 'center', flex: 1 }]}>
        {
          !authors.length && <CenterMessage message='No saved authors!'/>
        }
        {
          authors.map((item, index) => (
            <TouchableWithoutFeedback
              onPress={() => this.navigate(item)} key={index} > 
              <View style={styles.authorContainer}>
                <Text style={styles.author}>{item.firstName} {item.lastName}</Text>
              </View>
            </TouchableWithoutFeedback>
          ))     
        }
        </View>  
      </ScrollView>    
    );
  }
}

const styles = StyleSheet.create({
  authorContainer: {
    padding: 10,
    borderBottomWidth: 2, 
    borderBottomColor: colors.primary
  }, 
  author: {
    fontSize: 20, 
  },
  country: {
    color: 'rgba(0, 0, 0, .5)'
    },
});
