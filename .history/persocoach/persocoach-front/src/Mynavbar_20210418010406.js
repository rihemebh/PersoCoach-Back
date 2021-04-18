import React, { Component } from 'react';
import {Nav,  Navbar,NavDropdown} from 'react-bootstrap';


export default class Mynavbar extends Component {
  render() {
    return (
      <Navbar bg="white" expand="lg">
      <Navbar.Brand href="#home">React-Bootstrap</Navbar.Brand>
      <Navbar.Toggle aria-controls="basic-navbar-nav" />
      <Navbar.Collapse id="basic-navbar-nav">
        <Nav className="mr-auto">
          <Nav.Link href="#home">Home</Nav.Link>
          <Nav.Link href="#link">Coaches</Nav.Link>
          <Nav.Link href="#link">Coaches</Nav.Link>
          
        </Nav>
        
      </Navbar.Collapse>
    </Navbar>
    )
  }
}

