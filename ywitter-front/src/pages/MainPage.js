import Feed from '../components/assets/feed/Feed';
import { Searchbar } from '../components/assets/searchbar/Searchbar';
import React from 'react';
import { Row, Col } from 'react-bootstrap';
import Container from 'react-bootstrap/Container';

import CustomNavbarComponent from '../components/assets/navbar/CustomNavbar';

export const MainPage = () => {
  return (
    <Container fluid style={{ height: '100vh' }}>
      <Row>
        <Col xs={12} md={4}>
          {/* Navbar component */}
          <CustomNavbarComponent />
        </Col>
        <Col xs={12} md={4}>
          {/* Feed component */}
          <Feed />
        </Col>
        <Col xs={12} md={4}>
          {/* Searchbar component */}
          <Searchbar />
        </Col>
      </Row>
    </Container>
  );
};


