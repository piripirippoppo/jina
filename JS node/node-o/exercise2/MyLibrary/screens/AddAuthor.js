import React from 'react';
import {StyleSheet, Text, View, TextInput, TouchableOpacity} from 'react-native';
import uuidV4 from 'uuid/v4';
import { colors } from './../theme';

export default class AddAuthor extends React.Component {
  //TODO create the state object that should have a firstName and
  // lastName properties
    state = {
      firstName: '',
      lastName: ''
    }

  //TODO Understand the syntax in this event handler
  onChangeText = (key, value) => {
    this.setState({ [key]: value }) }
   
   //TODO complete the submit event handler
   // if either of the inputs is empty it should display an
   //alert window with the message: Please complete form
   // question you should be able to answer - what purpose 
   // does uidV4 serve??
  submit = () => {
   if (this.state.firstName === '' || this.state.lastName === ''){
    // add your code here
      alert('Please complete form')
   } else {
        const author = {
          firstName: this.state.firstName, 
          lastName: this.state.lastName, 
          id: uuidV4(),
          books: []
        }
        this.props.screenProps.addAuthor(author) 
        this.setState({
          firstName: '',
          lastName: '' }, () => {
          this.props.navigation.navigate('Authors') 
        })
      }
  }

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.heading}>Authors</Text>
        <TextInput
          placeholder='First Name of Author'
          onChangeText={val => this.onChangeText('firstName', val)} style={styles.input}
          value={this.state.firstName}
        />
        <TextInput
          placeholder='Last Name of Author'
          onChangeText={val => this.onChangeText('lastName', val)} style={styles.input}
          value={this.state.lastName}
        />
        <TouchableOpacity onPress={this.submit}>
          <View style={styles.button}>
            <Text style={styles.buttonText}>Add Author</Text>
          </View>
        </TouchableOpacity>  
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: colors.primary,
    justifyContent: 'center',
  },
  button: {
    height: 50, 
    backgroundColor: '#666', 
    justifyContent: 'center', 
    alignItems: 'center', 
    margin: 10, 
  }, 
  buttonText: {
    color: 'white',
    fontSize: 18 
  },
  heading: {
    color: 'white',
    fontSize: 40, 
    marginBottom: 10, 
    alignSelf: 'center'
  },
  input: {
    margin: 10, 
    backgroundColor: 'white', 
    paddingHorizontal: 8, 
    height: 50, 
  }
});
