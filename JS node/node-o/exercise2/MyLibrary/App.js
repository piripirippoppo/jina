/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React from 'react';
import AppNavigator from './routes/AppNavigator';

export default class App extends React.Component {
  //TODO create the state object with an authors property set to an empty array
  constructor(props) {
    super(props);

    this.state = {
      authors: []
    };

   // this.addAuthor = this.addAuthor.bind(this);
   // this.addBook = this.addBook.bind(this);
  }

  //TODO Define the addAuthor function 
  // it will need to update the state to add a new author to the state
  addAuthor = author => {
    const authors = this.state.authors
    authors.push(author)
    this.setState({
      authors
    })
  }

  //TODO define the addBook function. It takes an author and an index and 
  // adds a new book title to the books property of the author
  addBook = (title, author) => {
    const ind = this.state.authors.findIndex(ele => {
      return ele.id === author.id
    })
    const pick = this.state.authors[ind]
    pick.books.push(title)
    const authors = [
      ...this.state.authors.slice(0, ind),
      pick,
      ...this.state.authors.slice(ind + 1)
    ]
    this.setState({
      authors
    })
    }
  
  render() {
    return (
      <AppNavigator
        screenProps={{
          authors: this.state.authors,
          addAuthor: this.addAuthor,
          addBook: this.addBook,
        }}
      />
    );
  }
}
