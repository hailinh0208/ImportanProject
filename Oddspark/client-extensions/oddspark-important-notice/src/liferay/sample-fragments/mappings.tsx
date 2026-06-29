import React from 'react';
import ImportantNotice from './important-notice';

export const fragmentsMappings: Record<string, React.ComponentType<Record<string, unknown>>> = {
    'op-important-notice': ImportantNotice as React.ComponentType<Record<string, unknown>>,
};
