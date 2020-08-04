import React from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TouchableWithoutFeedback,
  TextInput,
  TouchableOpacity
} from 'react-native'
import CenterMessage from '../components/CenterMessage'
import { colors } from '../theme'

export default class Author extends React.Component {
  static navigationOptions = (props) => {
    const { author } = props.navigation.state.params
    return {
      title: `${author.firstName} ${author.lastName}`,
      headerStyle: {
        backgroundColor: colors.primary
      },
      headerTintColor: '#fff',
      headerTitleStyle: {
        color: 'white',
        fontSize: 20,
        fontWeight: '400'
      } 
    }
  }

  //TODO initialize the state object with the properties bookTitle
  // and comments
  constructor(props){
    super(props);

    this.state = {
      bookTitle: "",
      comments: ""
    }
      //this.onChangeText = this.onChangeText.bind(this);
      //this.addTitle = this.addTitle.bind(this);
    }

  // TODO complete the onChangeText handler - see the onChangeText handler 
  // in AddAuthor for help 
  onChangeText = (key, value) => {
    this.setState({ [key]: value }) 
  }

  //TODO complete the addTitle method
  // complete the if statement so that it simply returns if either the 
  //bookTitle or comments in the form are empty
  addTitle = () => {
    if (this.state.bookTitle === '' || this.state.comments === '') return
    const { author } = this.props.navigation.state.params
    const book = {
      bookTitle: this.state.bookTitle,
      comments: this.state.comments
    }
    this.props.screenProps.addBook(book, author)

    //TODO reset the state upon successful addition of a title to an author
    this.setState({ bookTitle: '', comments: ''})
  }

  render() {
    const { author } = this.props.navigation.state.params
    return (
      <View style={{ flex: 1 }} >
        <ScrollView contentContainerStyle={[!author.books.length && { flex: 1 }]}>
          <View style={[!author.books.length && { flex: 1, justifyContent: 'center' }]}>
            {
              !author.books.length && <CenterMessage message='No books for this author!' />
            }
            {
              author.books.map((book, index) => (
                <View key={index} style={styles.bookContainer}>
                  <Text style={styles.title}>{book.bookTitle}</Text>
                  <Text style={styles.comment}>{book.comments}</Text>
                </View>
                )
              )
            }
          </View>
        </ScrollView>
        <TextInput
          onChangeText={val => this.onChangeText('bookTitle', val)}
          placeholder='Title name'
          value={this.state.bookTitle}
          style={styles.input}
          placeholderTextColor='white'
        />
        <TextInput
          onChangeText={val => this.onChangeText('comments', val)}
          placeholder='comments about this book'
          value={this.state.comments}
          style={[styles.input, styles.input2]}
          placeholderTextColor='white'
        />
        <View style={styles.buttonContainer}>
          <TouchableOpacity onPress={this.addTitle}>
            <View style={styles.button}>
              <Text style={styles.buttonText}>Add Book to Author</Text>
            </View>
          </TouchableOpacity>
        </View>
      </View>

    );
  }
}

const styles = StyleSheet.create({
  bookContainer: {
    padding: 10,
    borderBottomWidth: 2, 
    borderBottomColor: colors.primary
  },
  container: {
    flex: 1,
    backgroundColor: colors.primary,
    justifyContent: 'center',
  },
  button: {
    height: 50, 
    backgroundColor: colors.primary, 
    justifyContent: 'center', 
    alignItems: 'center',  
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
    backgroundColor: colors.primary, 
    paddingHorizontal: 8, 
    height: 50,
    borderBottomWidth: 2,
    borderBottomColor: 'white',

  },
  title: {
    fontSize: 20
  },
  comment: {
    color: 'rgba(0, 0, 0, .5)'
  }
});
