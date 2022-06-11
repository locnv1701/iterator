import React, { useState } from 'react';
import { useSprings } from 'react-spring';
import { toast } from 'react-toastify';
import { useGesture } from 'react-with-gesture';
import { useAppDispatch, useAppSelector } from 'store/hooks';
import {
  getCurrentApplicantPage,
  getCurrentPage,
  setCurrentApplicantPage,
  setCurrentPage,
} from 'utils/localStorage';
import { getApplicantList, getJobPosts, sendMatchRequest } from '../store/homeSlice';
import { JobCard } from './JobCard';

import './deckStyles.css';
import { ApplicantCard } from './ApplicantCard';
import { IApplicantQuery, IJobPostQuery } from '../types/homeTypes';

const imgs = [
  'https://images.unsplash.com/photo-1633113087654-c49c686c2cdf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDF8MHxlZGl0b3JpYWwtZmVlZHwxMXx8fGVufDB8fHx8&auto=format&fit=crop&w=500&q=60',
  'https://images.unsplash.com/photo-1638518945531-ff6290869fb2?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw5MXx8fGVufDB8fHx8&auto=format&fit=crop&w=500&q=60',
  'https://images.unsplash.com/photo-1638482856830-16b0e15fcf2c?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw5NHx8fGVufDB8fHx8&auto=format&fit=crop&w=500&q=60',
  'https://images.unsplash.com/photo-1638399832631-d5c4fd48dfcc?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwyMjR8fHxlbnwwfHx8fA%3D%3D&auto=format&fit=crop&w=500&q=60',
];

// const cards = [1, 2, 3, 4];

const to = (i: any) => ({
  x: 0,
  y: i * -10 - 40,
  scale: 1,
  rot: -10 + Math.random() * 20,
  delay: i * 100,
});
const from = (_i: any) => ({ x: 0, rot: 0, scale: 1.5, y: -1000 });

const trans = (r: any, s: any) =>
  `perspective(1500px) rotateX(30deg) rotateY(${r / 10}deg) rotateZ(${r}deg) scale(${s})`;

const Deck = ({
  requestJobId,
  cards,
  applicantQuery,
  jobPostQuery,
}: {
  requestJobId?: number;
  cards: number[];
  applicantQuery?: IApplicantQuery;
  jobPostQuery?: IJobPostQuery;
}) => {
  const dispatch = useAppDispatch();
  const [gone] = useState(() => new Set());

  const userId = useAppSelector((state) => state.authLogin.userId);
  const { applicantId } = useAppSelector((state) => state.authLogin);
  const jobList = useAppSelector((state) => state.home.jobList).map((job, index) => ({
    ...job,
    image: imgs[index],
  }));
  const applicantList = useAppSelector((state) => state.home.applicantList).map(
    (applicant, index) => ({
      ...applicant,
      image: imgs[index],
    }),
  );
  // const cards = !!applicantId
  //   ? [...new Array(jobList.length)].map((index) => index + 1)
  //   : [...new Array(applicantList.length)].map((ex, index) => index + 1);

  const [springs, set] = useSprings(cards.length, (i: any) => ({
    ...to(i),
    from: from(i),
  }));
  const bind = useGesture(
    ({ args: [index, item], down, delta: [xDelta], direction: [xDir], velocity }) => {
      const trigger = velocity > 0.2;

      const dir = xDir < 0 ? -1 : 1;

      if (!down && trigger) {
        gone.add(index);
        if (dir > 0) {
          dispatch(
            sendMatchRequest({
              userId: userId,
              target: {
                job_post_id: applicantId ? item.job_post_id : requestJobId,
                target_user_id: applicantId ? item.sharer_id : item?.user_id || 0,
              },
            }),
          );
        } else {
          toast.info('Skipped!!!', {
            position: toast.POSITION.TOP_RIGHT,
          });
        }
      }

      set((i: any) => {
        if (index !== i) return;
        const isGone = gone.has(index);

        const x = isGone ? (200 + window.innerWidth) * dir : down ? xDelta : 0;

        const rot = xDelta / 100 + (isGone ? dir * 10 * velocity : 0);

        const scale = down ? 1.1 : 1;
        return {
          x,
          rot,
          scale,
          delay: undefined,
          config: { friction: 50, tension: down ? 800 : isGone ? 200 : 500 },
        };
      });

      if (!down && gone.size === cards.length) {
        if (!!applicantId) {
          const nextPage = Number(getCurrentPage(String(userId))) + 1;
          dispatch(getJobPosts({ data: nextPage, userId: userId, body: jobPostQuery }));
          setCurrentPage(String(userId), String(nextPage));
        } else {
          const nextPage = Number(getCurrentApplicantPage(String(requestJobId))) + 1;
          dispatch(
            getApplicantList({
              userId: userId,
              page: nextPage,
              jobId: requestJobId,
              body: applicantQuery,
            }),
          );
          setCurrentApplicantPage(String(requestJobId), String(nextPage));
        }
        /*@ts-ignore*/
        setTimeout(() => gone.clear() || set((i: any) => to(i)), 1000);
      }
    },
  );

  return (
    <div className="wrapper">
      <div className="content">
        {springs.map(({ x, y, rot, scale }, i) =>
          applicantId ? (
            <JobCard
              key={i}
              i={i}
              objs={jobList}
              x={x}
              y={y}
              rot={rot}
              scale={scale}
              trans={trans}
              cards={cards}
              bind={bind}
            />
          ) : (
            <ApplicantCard
              key={i}
              i={i}
              objs={applicantList}
              x={x}
              y={y}
              rot={rot}
              scale={scale}
              trans={trans}
              cards={cards}
              bind={bind}
            />
          ),
        )}
      </div>
    </div>
  );
};

export default Deck;
