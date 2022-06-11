import React from 'react';
import Carousel from 'nuka-carousel';
import { animated, to as interpolate } from '@react-spring/web';
import { IExtendedJobPost } from '../types/homeTypes';

interface Props {
  i: any;
  rot: any;
  scale: any;
  trans: any;
  objs: IExtendedJobPost[];
  x: any;
  y: any;
  bind: any;
  cards: any;
}

export const JobCard = (props: Props) => {
  const { i, rot, scale, trans, objs, x, y, bind } = props;
  const { company, image, job_description, job_location, job_salary, job_type } = objs[i] || {};

  return (
    <animated.div
      style={{
        transform: interpolate([x, y], (x, y) => `translate3d(${x}px,${y}px,0)`),
      }}
    >
      <animated.div
        {...bind(i, objs[i])}
        style={{
          transform: interpolate([rot, scale], trans),
        }}
      >
        <div className="card">
          <Carousel>
            <img src={image} alt="profilePicture" />
          </Carousel>
          <div className="card-desc">
            <h2>{company?.company_name}</h2>
            <h4>{job_type}</h4>
            <h5>Up to {job_salary}$</h5>
            <h5>{job_location}</h5>
            <h5>{company?.profile_description}</h5>
            <h5>{job_description}</h5>
          </div>
        </div>
      </animated.div>
    </animated.div>
  );
};
