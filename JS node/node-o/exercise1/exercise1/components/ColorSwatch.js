import React, {Component} from 'react'
import {Alert, Button, SafeAreaView, ScrollView, StatusBar, StyleSheet, View, Text, TextInput } from 'react-native'
import {
  Colors
} from 'react-native/Libraries/NewAppScreen';

class ColorSwatch extends Component {
  /* define your constructor and all necessary functions here  */
   //The main difference between state and props is that props are immutable.//
    constructor(props){
     super(props);
     this.state = { 
        title: 'Exercise 1',
        text: ''
      }};
   
    //TODO
    //you need to define a updateStyle function handler
    //hint - you need to bind it to a property in the construcor of the component
    updateStyle(text) {
      this.setState({ text: text });
    }
    //Define the resetStyle function that will reset the state of your component
    resetStyle() {
     this.setState({ text: '' });
    }
    
    render() {
      return (
        <>
          <StatusBar barStyle="dark-content" />
          <SafeAreaView>
            <ScrollView
              contentInsetAdjustmentBehavior="automatic"
              style={styles.scrollView}>
              <View style={styles.body}>
                <View style={styles.sectionContainer}>
                {/* this is a comment in jsx */}
                {/* TODO You need to have the <Text> component display the title from the state of the object */}  
                  <Text style={styles.sectionTitle}>{this.state.title}</Text>
                  {/* TODO You need to have the <Text> component display what has currently been entered in the input field */}
                  <Text style={styles.sectionTitle}> The color entered is: {this.state.text}</Text>
                </View>
                {/* TODO have the <TextInput> component attach a handler to the onChangeText event */}
                {/*  TODO set the value property equal to the initial state property you are updating */}
                <TextInput
                style={{ height: 40, borderColor: 'gray', borderWidth: 1 }}
                onChangeText={text => this.updateStyle(text)}
                value={this.state.text}
                />
                <View style={styles.fixToText}>
                  {/* TODO replace 'Debug' with individual state properties to do some debugging */}
                  <Button
                    title="Check my state"
                    onPress={() => Alert.alert(this.state.text)}
                  />
                  {/*TODO apply a handler to reset the component's state property you are storing the input in*/}
                  <Button
                    title="Reset my state"
                    onPress={() => this.resetStyle()}
                    
                  />
                </View>
                {/* TODO add a property to the style object to display the color entered  */}
                {/* TODO display the entered text inside of the <Text> component */}
                <View style={{padding: 30, alignSelf: 'center', backgroundColor: this.state.text.toString().toLowerCase()}}>
                  <Text style={styles.sectionDescription}>{this.state.text}</Text>
                </View>
              </View>
            </ScrollView>
          </SafeAreaView>
        </>
      )
    }
}

const styles = StyleSheet.create({
  scrollView: {
    backgroundColor: Colors.lighter,
  },
  engine: {
    position: 'absolute',
    right: 0,
  },
  body: {
    backgroundColor: 'white',
    flex: 1
  },
  sectionContainer: {
    marginTop: 64,
    paddingHorizontal: 24,
    flex: 1
  },
  sectionTitle: {
    fontSize: 34,
    fontWeight: '600',
    color: Colors.black,
    textAlign: 'center',
    flex: 1
  },
  sectionDescription: {
    marginTop: 8,
    fontSize: 18,
    fontWeight: '400',
    color: Colors.dark,
  },
  highlight: {
    fontWeight: '700',
  },
  footer: {
    color: Colors.dark,
    fontSize: 12,
    fontWeight: '600',
    padding: 4,
    paddingRight: 12,
    textAlign: 'right',
  },
  fixToText: {
    flexDirection: 'row',
    justifyContent: 'space-between',
  },
});

export default ColorSwatch