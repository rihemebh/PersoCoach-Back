import React, { Component } from 'react';
import {Nav,  Navbar,NavDropdown} from 'react-bootstrap';


export default class Mynavbar extends Component {
  render() {
    return (
      <Navbar bg="white" expand="lg">
    
      <Navbar.Toggle aria-controls="basic-navbar-nav" />
      <Navbar.Collapse >
        <Nav className="mr-auto" id="basic-navbar-nav">
          <Nav.Link href="#home">Home</Nav.Link>
          <Nav.Link href="#link">Coaches</Nav.Link>
        </Nav>
        <Navbar.Brand href="#home">React-Bootstrap</Navbar.Brand>
        <Nav className="mr-auto" id="basic-navbar-nav">
        <Nav.Link href="#link">Community</Nav.Link>
          <Nav.Link href="#link">Contact</Nav.Link>
          </Nav>
      </Navbar.Collapse>
    </Navbar>
    )
  }
}

