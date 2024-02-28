import Feed from '../components/assets/feed/Feed';
import { Searchbar } from '../components/assets/searchbar/Searchbar';
import React from 'react';
import { Row, Col } from 'react-bootstrap';
import Container from 'react-bootstrap/Container';

import CustomNavbarComponent from '../components/assets/navbar/CustomNavbar';
import ForYouFollowing from '../components/assets/feed/ForYouFollowing';

export const MainPage = () => {
  return (
    <Container fluid style={{ height: '100vh' }}>
      <Row>
        <Col xs={12} md={4}>
          <CustomNavbarComponent />
        </Col>
        <Col xs={12} md={4}>
          <ForYouFollowing/>
          <Feed />
        </Col>
        <Col xs={12} md={4}>
          <Searchbar />
        </Col>
      </Row>
    </Container>
  );
};


